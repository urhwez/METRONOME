package com.example.metronome.Models;

import androidx.room.*;

import java.util.List;

@Dao
public interface ChordInterface {
    @Insert
    void insertChord(Chord... chords);

    @Delete
    void deleteChord(Chord chord);

    @Query("SELECT * from chord")
    List<Chord> getAllChord();

}
