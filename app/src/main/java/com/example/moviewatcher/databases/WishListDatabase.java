package com.example.moviewatcher.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviewatcher.models.Movie;

@Database(entities = {Movie.class}, version = 3, exportSchema = false)
public abstract class WishListDatabase extends RoomDatabase {

    private static WishListDatabase instance;

    public abstract MovieDao movieDao();

    public static synchronized WishListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WishListDatabase.class, "wish_list_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
