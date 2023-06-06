package com.example.metronome;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private TextView BPM;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;

    private int a;

    private ImageButton back;

    public Metronome metronome;

    private ImageButton play, pause;
    public static MediaPlayer sound1;
    public static MediaPlayer sound2;


    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metronome = new Metronome();

        BPM = findViewById(R.id.id_bpm);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);


        ImageButton play = (ImageButton) findViewById(R.id.play);
        ImageButton stop = (ImageButton) findViewById(R.id.stop);
        ImageButton back = (ImageButton) findViewById(R.id.back);


        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView textView = findViewById(R.id.id_bpm);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sound1 = MediaPlayer.create(getApplicationContext(), R.raw.sound1);
        sound2 = MediaPlayer.create(getApplicationContext(),R.raw.sound2);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bpm = seekBar.getProgress();
                metronome.stp();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), act1.class);
                startActivity(intent);

            }
        });









        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                a = 2;
                metronome.stp();
            }
        });

        image3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 3;
                metronome.stp();
            }
        });
        image4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 4;
                metronome.stp();
            }
        });
        image5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 5;
                metronome.stp();
            }
        });
        image6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 6;
                metronome.stp();
            }
        });
        image7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 7;
                metronome.stp();
            }
        });
        image8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                a = 8;
                metronome.stp();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bpm = seekBar.getProgress();
                metronome.play(bpm, a);
//                    metronome.playSound(bpm, a);
            }
        });

    }


    private class Metronome extends Thread{
        private int bpm;
        private int tack;

        private int counterTack = 1;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                play(bpm, tack);
            }
        };

        public void play(int bpm, int tack) {
            this.bpm = bpm;
            this.tack = tack;

            if (this.counterTack == 1) {
                sound1.start();
                this.counterTack++;
            } else {
                sound2.start();
                this.counterTack = this.counterTack == this.tack ? 1 : this.counterTack + 1;
            }

            handler.postDelayed(runnable, (int) ((60.0 / bpm) * 1000));
        }

        public void stp(){
            handler.removeCallbacks(runnable);
            this.counterTack = 1;
        }
    }
}






