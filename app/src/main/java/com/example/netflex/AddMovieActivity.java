package com.example.netflex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddMovieActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextYear, editTextImdbID, editTextType, editTextPosterUrl;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextYear = findViewById(R.id.editTextYear);
        editTextImdbID = findViewById(R.id.editTextImdbID);
        editTextType = findViewById(R.id.editTextType);
        editTextPosterUrl = findViewById(R.id.editTextPosterUrl);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Handle button click
        buttonSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String year = editTextYear.getText().toString().trim();
            String imdbID = editTextImdbID.getText().toString().trim();
            String type = editTextType.getText().toString().trim();
            String posterUrl = editTextPosterUrl.getText().toString().trim();

            // Validate inputs
            if (title.isEmpty() || year.isEmpty() || imdbID.isEmpty() || type.isEmpty() || posterUrl.isEmpty()) {
                Toast.makeText(AddMovieActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new Movie object and pass it back
                Movie newMovie = new Movie(title, year, imdbID, type, posterUrl);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NEW_MOVIE", newMovie);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Go to Home (MainActivity)
                Intent homeIntent = new Intent(AddMovieActivity.this, MainActivity.class);
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
