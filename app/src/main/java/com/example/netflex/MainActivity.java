// File: MainActivity.java
package com.example.netflex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FloatingActionButton fabAddMovie;
    private static final int REQUEST_CODE_ADD_MOVIE = 1;

    // Database and executor for background operations
    private MovieDatabase movieDatabase;
    private ExecutorService databaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        fabAddMovie = findViewById(R.id.fabAddMovie);

        // Initialize the database and executor
        movieDatabase = MovieDatabase.getInstance(this);
        databaseExecutor = Executors.newSingleThreadExecutor();

        // Initialize the list and load movies from the database
        movieList = new ArrayList<>();
        loadMoviesFromDatabase();

        // Initialize the adapter and RecyclerView
        movieAdapter = new MovieAdapter(this, movieList, movie -> {
            // Handle item click to open movie details
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("MOVIE_DETAIL", movie);
            startActivity(intent);
        });

        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(movieAdapter);

        // Enable swipe-to-delete functionality
        enableSwipeToDelete();

        // FAB click listener to add a new movie
        fabAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MOVIE);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Go to Home (MainActivity)
                Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
                return true;

            } else if (itemId == R.id.navigation_back) {
                // Go back to the previous activity
                onBackPressed();
                return true;
            }

            return false;
        });

    }

    // Load movies from the database
    private void loadMoviesFromDatabase() {
        databaseExecutor.execute(() -> {
            List<Movie> movies = movieDatabase.movieDAO().getAllMovies();
            runOnUiThread(() -> {
                movieList.clear();
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            });
        });
    }

    // Method to enable swipe to delete functionality
    private void enableSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No move functionality needed
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Movie deletedMovie = movieList.get(position);

                // Remove movie from the list and notify adapter
                movieList.remove(position);
                movieAdapter.notifyItemRemoved(position);

                // Remove movie from database in background
                databaseExecutor.execute(() -> movieDatabase.movieDAO().deleteMovie(deletedMovie));

                // Show Snackbar with undo option
                Snackbar.make(recyclerViewMovies, deletedMovie.getTitle() + " deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            movieList.add(position, deletedMovie);
                            movieAdapter.notifyItemInserted(position);
                            databaseExecutor.execute(() -> movieDatabase.movieDAO().insertMovie(deletedMovie));
                        }).show();
            }
        };

        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewMovies);
    }

    // Handle result from AddMovieActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            Movie newMovie = (Movie) data.getSerializableExtra("NEW_MOVIE");
            if (newMovie != null) {
                // Add the new movie to the list and update the UI
                movieList.add(newMovie);
                movieAdapter.notifyItemInserted(movieList.size() - 1);

                // Insert the new movie into the database in a background thread
                databaseExecutor.execute(() -> movieDatabase.movieDAO().insertMovie(newMovie));
            }
        }
    }

    // Optional: Cleanup resources by shutting down the executor
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }
}
