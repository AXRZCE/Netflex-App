package com.example.netflex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle, textViewYear, textViewImdbID, textViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Initialize views
        imageViewPoster = findViewById(R.id.imageViewPosterDetail);
        textViewTitle = findViewById(R.id.textViewTitleDetail);
        textViewYear = findViewById(R.id.textViewYearDetail);
        textViewImdbID = findViewById(R.id.textViewImdbIDDetail);
        textViewType = findViewById(R.id.textViewTypeDetail);

        // Get movie data from intent
        Movie movie = (Movie) getIntent().getSerializableExtra("MOVIE_DETAIL");

        if (movie != null) {
            textViewTitle.setText(movie.getTitle());
            textViewYear.setText("Year: " + movie.getYear());
            textViewImdbID.setText("IMDb ID: " + movie.getImdbID());
            textViewType.setText("Type: " + movie.getType());

            // Load poster image using Glide
            Glide.with(this).load(movie.getPosterUrl()).into(imageViewPoster);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Go to Home (MainActivity)
                Intent homeIntent = new Intent(MovieDetailActivity.this, MainActivity.class);
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
}
