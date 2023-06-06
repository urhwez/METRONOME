package com.example.metronome;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import com.example.metronome.Models.AppDatabase;
import com.example.metronome.Models.Chord;
import com.example.metronome.Models.ChordInterface;
import com.google.android.material.resources.TextAppearance;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class databaseActivity extends AppCompatActivity {

    private TableLayout table;

    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        this.player = new MediaPlayer();


        this.table = findViewById(R.id.table);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "chord").allowMainThreadQueries().build();
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "chord").fallbackToDestructiveMigration().build();


        List<Chord> data = db.chord().getAllChord();
//
//         for (Chord chord: data) {
//            db.chord().deleteChord(chord);
//        }

        if (data.isEmpty()) {
            seederDatabase();
            data = db.chord().getAllChord();
        }

        try {
            buildTable(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void seederDatabase() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "chord").allowMainThreadQueries().build();
        List<String[]> defaultParams = new ArrayList<>();

       defaultParams.add(new String[]{"0", "Am", "Am.mp3", "Am.png"});
       defaultParams.add(new String[]{"1", "C", "C.mp3", "C.png"});
       defaultParams.add(new String[]{"2", "E", "E.mp3", "E.png"});
       defaultParams.add(new String[]{"3", "D", "D.mp3", "D.png"});
       defaultParams.add(new String[]{"4", "A", "A.mp3", "A.png"});
       defaultParams.add(new String[]{"5", "Em", "Em.mp3", "Em.png"});
       defaultParams.add(new String[]{"6", "Dm", "Dm.mp3", "Dm.png"});
       defaultParams.add(new String[]{"7", "Bm", "Bm.mp3", "Bm.png"});
       defaultParams.add(new String[]{"8", "F", "F.mp3", "F.png"});
       defaultParams.add(new String[]{"9", "G", "G.mp3", "G.png"});


        for (String[] data: defaultParams) {
            Chord chord = new Chord();
            chord.id = Integer.parseInt(data[0]);
            chord.name = data[1];
            chord.sound_path = data[2];
            chord.image_path = data[3];


            db.chord().insertChord(chord);
        }
    }

    protected void buildTable(List<Chord> data) throws IOException {
        this.table.removeAllViews();

        for (Chord chord: data) {
            TableRow row = new TableRow(this);

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(this);
            tv.setText(chord.name);
            tv.setTextSize(32);

            try {
                ImageView image = new ImageView(getApplicationContext());
                InputStream is = getAssets().open(chord.image_path);

                image.setImageDrawable(Drawable.createFromStream(is, null));
                row.addView(image);
                is.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            ImageButton ib = new ImageButton(this);
            ib.setImageResource(R.drawable.start);
            ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ib.setBackgroundColor(Color.TRANSPARENT);
            ib.setLayoutParams(new TableRow.LayoutParams(120, 120));

            databaseActivity _this = this;
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        _this.player.reset();
                        AssetFileDescriptor afd = getResources().getAssets().openFd(chord.sound_path);
                        _this.player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        _this.player.prepare();
                        _this.player.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            row.addView(tv);
            row.addView(ib);

            this.table.addView(row);
        }
    }
}