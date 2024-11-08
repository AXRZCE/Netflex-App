package com.example.netflex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;
    private FloatingActionButton fabAddMovie;
    private static final int REQUEST_CODE_ADD_MOVIE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and FloatingActionButton
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        fabAddMovie = findViewById(R.id.fabAddMovie);

        // Initialize movie list and adapter
        movieList = new ArrayList<>();
        initializeMockData();  // Method to populate movieList with mock data
        movieAdapter = new MovieAdapter(this, movieList, movie -> {
            // Handle item click to open movie details
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("MOVIE_DETAIL", movie);
            startActivity(intent);
        });

        // Set up RecyclerView with adapter
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(movieAdapter);

        // Enable swipe-to-delete functionality
        enableSwipeToDelete();

        // FloatingActionButton click listener to add a new movie
        fabAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MOVIE);
        });
    }

    private void initializeMockData() {
        movieList.add(new Movie("The Batman", "2022", "tt1877830", "Movie", "https://example.com/thebatman.jpg"));
        movieList.add(new Movie("Inception", "2010", "tt1375666", "Movie", "https://example.com/inception.jpg"));
        movieList.add(new Movie("Fight Club", "1999", "tt0137523", "Movie", "https://example.com/fightclub.jpg"));
        movieList.add(new Movie("Interstellar", "2014", "tt0816692", "Movie", "https://example.com/interstellar.jpg"));
        movieList.add(new Movie("The Godfather", "1972", "tt0068646", "Movie", "https://example.com/thegodfather.jpg"));
        // Add more movies as needed
    }

    private void enableSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We don’t need drag & drop functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Movie deletedMovie = movieList.get(position);

                // Remove item from the list and notify adapter
                movieList.remove(position);
                movieAdapter.notifyItemRemoved(position);

                // Show Snackbar with undo option
                Snackbar.make(recyclerViewMovies, deletedMovie.getTitle() + " deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            movieList.add(position, deletedMovie);
                            movieAdapter.notifyItemInserted(position);
                        })
                        .show();
            }
        };

        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewMovies);
    }

    // Handle result from AddMovieActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK) {
            Movie newMovie = (Movie) data.getSerializableExtra("NEW_MOVIE");
            if (newMovie != null) {
                movieList.add(newMovie);
                movieAdapter.notifyItemInserted(movieList.size() - 1);
            }
        }
    }
}
