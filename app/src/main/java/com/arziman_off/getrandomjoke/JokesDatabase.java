package com.arziman_off.getrandomjoke;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {JokeItemInfo.class}, version = 1)
public abstract class JokesDatabase extends RoomDatabase {
    private static final String DB_NAME = "jokes.db";
    private static JokesDatabase instance = null;
    public static JokesDatabase getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(
                    application,
                    JokesDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract JokesDao jokesDao();

}


