package com.arziman_off.getrandomjoke;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JokesDao {
    @Query("SELECT * FROM jokeItems")
    List<JokeItemInfo> getJokes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(JokeItemInfo jokeItem);

    @Query("DELETE FROM jokeItems WHERE id = :id")
    void remove(int id);
}
