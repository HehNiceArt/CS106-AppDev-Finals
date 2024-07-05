package com.example.nyanyanko;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private static final String TAG = "GameView";
    private NyankoAI nyankoAI;
    private Thread gameThread = null;
    private boolean isPlaying;
    private SurfaceHolder surfaceHolder;
    private Bitmap background;
    private int screenWidth;
    private int screenHeight;

    public int initialX, initialY;
    private int hp = 10;
    private int hunger = 10;
    private String mood = "Good";
    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();

        surfaceCreated(surfaceHolder);
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        screenWidth = getWidth();
        screenHeight = getHeight();

        Bitmap nyankoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nyanko);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.gameviewbg);

        if(nyankoBitmap != null){
            Bitmap resized = Bitmap.createScaledBitmap(nyankoBitmap, 240, 240, false);
            initialX = (screenWidth - nyankoBitmap.getWidth()) / 2;
            initialY = (screenHeight - nyankoBitmap.getHeight()) / 2;
            nyankoAI = new NyankoAI(resized, initialX, initialY,  0.6f);
            Log.d(TAG, "cat spawning");
        }else{
            Log.e(TAG, "cat is null!");
        }
        resume();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run()
    {
        while(isPlaying)
        {
            update();
            draw();
            control();
        }
    }
    public void update()
    {
        if(nyankoAI != null){
            nyankoAI.update();
        }else {
            Log.e(TAG, "NyankoAI is null in update()!");
        }
    }
    public void draw()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);
                canvas.drawBitmap(background, 0, 0, null);
            if(nyankoAI != null){
                nyankoAI.draw(canvas);
                canvas.drawCircle(screenWidth / 2, screenHeight / 2, 50, new Paint());
            }else {
                Log.e(TAG, "NyankoAI is null in draw()");
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void control()
    {
        try{
            Thread.sleep(17);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void resume()
    {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void pause()
    {
        isPlaying = false;
        try{
            gameThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void handleShopBTNClick()
    {
        hp = Math.max(0, hp - 1);
    }
    public void handleToyBTNClick()
    {
        hunger = Math.max(0, hunger -1);
    }
    public void handleInventoryBTNClick()
    {
    }
    public void handleMenuBTNClick()
    {

    }
}
