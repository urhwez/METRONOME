package com.example.metronome.Models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Chord.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChordInterface chord();
}

