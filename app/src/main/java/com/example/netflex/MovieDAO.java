package com.example.netflex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {

    // Insert a new movie
    @Insert
    void insertMovie(Movie movie);

    // Update an existing movie
    @Update
    void updateMovie(Movie movie);

    // Delete a specific movie
    @Delete
    void deleteMovie(Movie movie);

    // Delete all movies (useful for clearing the list)
    @Query("DELETE FROM movies")
    void deleteAllMovies();

    // Retrieve all movies
    @Query("SELECT * FROM movies ORDER BY title ASC")
    List<Movie> getAllMovies();

    // Retrieve a movie by its ID (useful for detail screen)
    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    Movie getMovieById(int id);
}
