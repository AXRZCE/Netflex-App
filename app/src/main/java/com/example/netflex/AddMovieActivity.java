// File: AddMovieActivity.java
package com.example.netflex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                // Pass data back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", title);
                resultIntent.putExtra("year", year);
                resultIntent.putExtra("imdbID", imdbID);
                resultIntent.putExtra("type", type);
                resultIntent.putExtra("posterUrl", posterUrl);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
