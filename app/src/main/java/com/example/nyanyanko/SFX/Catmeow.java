package com.example.nyanyanko.SFX;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nyanyanko.R;

public class Catmeow extends AppCompatActivity{
    private MediaPlayer sfxPlayer;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        sfxPlayer = MediaPlayer.create(this, R.raw.catmeow1);
        // Initialize MediaPlayer with the sound file
        if(handler == null){
            Log.d("Catmeow", "Handler initialized Failed");
        }else{
            Log.e("Catmeow", "Handler initialization success");
        }

        // Initialize Handler to schedule periodic playback
        startPeriodicSfx();
    }

    //TODO every 8 sec, play meow
    //Problem: Can't call on NyankoAI
    public void startPeriodicSfx() {
        // Define the delay (in milliseconds) between each playback
        handler = new Handler();
        long delayMillis = 8000; // 8 seconds

        // Runnable to play the SFX
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (sfxPlayer != null) {
                    sfxPlayer.seekTo(0); // Rewind to the beginning (if needed)
                    sfxPlayer.start(); // Start playing the sound
                }
                // Schedule the next playback after delayMillis
                handler.postDelayed(this, delayMillis);
            }
        };

        // Schedule the first playback immediately
        if(handler != null){
            handler.post(runnable);
        }else{
            Log.d("catmeow", "handler null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer and Handler when the activity is destroyed
        if (sfxPlayer != null) {
            sfxPlayer.release();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
