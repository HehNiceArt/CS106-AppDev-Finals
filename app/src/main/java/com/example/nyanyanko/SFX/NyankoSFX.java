package com.example.nyanyanko.SFX;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nyanyanko.R;

public class NyankoSFX extends AppCompatActivity {
    private Handler handler;
    private MediaPlayer sfxPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        sfxPlayer = MediaPlayer.create(this, R.raw.catmeow1);
        if(handler == null){
            Log.d("catmeow", "handler init failed");
        }
       startPeriodicSfx();
    }

    public void startPeriodicSfx() {
        long delayMillis = 8000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(sfxPlayer != null){
                    sfxPlayer.seekTo(0);
                    sfxPlayer.start();
                }
                handler.postDelayed(this, delayMillis);
            }
        };
        if(handler != null){
           handler.post(runnable);
        }
    }
}
