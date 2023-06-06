package com.example.metronome.Models;

import androidx.room.*;

@Entity
public class Chord {
    public @PrimaryKey int id;
    public String name;
    public String sound_path;
    public String image_path;
}
