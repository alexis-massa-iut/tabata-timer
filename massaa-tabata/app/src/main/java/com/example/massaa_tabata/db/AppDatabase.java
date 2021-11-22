package com.example.massaa_tabata.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Seance.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SeanceDAO seanceDAO();

}