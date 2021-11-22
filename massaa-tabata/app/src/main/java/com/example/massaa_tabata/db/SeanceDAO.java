package com.example.massaa_tabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeanceDAO {

    @Query("SELECT * FROM seance")
    List<Seance> getAll();

    @Insert
    long insert(Seance seance);

    @Insert
    long[] insertAll(Seance... seances);

    @Delete
    void delete(Seance seance);

    @Update
    void update(Seance seance);

}