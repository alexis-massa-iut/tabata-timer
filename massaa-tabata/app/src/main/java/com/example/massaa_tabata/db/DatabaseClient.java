package com.example.massaa_tabata.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    // Unique instance to link database
    private static DatabaseClient instance;

    // Database object
    private AppDatabase appDatabase;

    // Constructor
    private DatabaseClient(final Context context) {

        // Create Database object using "Room database builder"
        // TabataDB est le nom de la base de données
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "TabataDB").build();

        ////////// Fill Database on creation using "roomDatabaseCallback"
        // Adding method addCallback to populate database on creation
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "TabataDB").addCallback(roomDatabaseCallback).build();
    }

    // Static method
    // returns the DatabaseClient instance
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // returns database object
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Object to populate database on creation
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };
}
