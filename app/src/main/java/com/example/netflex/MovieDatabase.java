package com.example.netflex;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Define the entities (tables) and database version
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    // Access point for DAO methods
    public abstract MovieDAO movieDAO();

    // Singleton instance
    private static volatile MovieDatabase INSTANCE;

    // Method to get the database instance
    public static synchronized MovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie_database")
                    .fallbackToDestructiveMigration()  // Destroys old data if schema changes
                    .build();
        }
        return INSTANCE;
    }
}
