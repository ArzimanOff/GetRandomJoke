package com.arziman_off.getrandomjoke;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JokesDao {
    @Query("SELECT * FROM likedJokes")
    List<JokeItemInfo> getLikedJokes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(JokeItemInfo likedJokeItem);

    @Query("DELETE FROM likedJokes WHERE id = :id")
    void remove(int id);
}
