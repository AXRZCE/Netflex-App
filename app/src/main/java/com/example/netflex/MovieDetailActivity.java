package com.example.netflex;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle, textViewYear, textViewImdbID, textViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imageViewPoster = findViewById(R.id.imageViewPosterDetail);
        textViewTitle = findViewById(R.id.textViewTitleDetail);
        textViewYear = findViewById(R.id.textViewYearDetail);
        textViewImdbID = findViewById(R.id.textViewImdbIDDetail);
        textViewType = findViewById(R.id.textViewTypeDetail);

        Movie movie = (Movie) getIntent().getSerializableExtra("MOVIE_DETAIL");

        if (movie != null) {
            textViewTitle.setText(movie.getTitle());
            textViewYear.setText("Year: " + movie.getYear());
            textViewImdbID.setText("IMDb ID: " + movie.getImdbID());
            textViewType.setText("Type: " + movie.getType());

            Glide.with(this).load(movie.getPosterUrl()).into(imageViewPoster);
        }
    }
}
