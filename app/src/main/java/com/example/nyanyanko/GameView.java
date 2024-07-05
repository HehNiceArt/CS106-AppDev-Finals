package com.example.nyanyanko;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


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

    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();

        surfaceCreated(surfaceHolder);
        surfaceHolder.addCallback(this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        init();
        resume();
        buttonList();
    }
    private void init(){
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
    }
    private void buttonList(){
        Button toyBTN = (Button) findViewById(R.id.toyBTN);
        Button menuBTN = (Button) findViewById(R.id.menuBTN);
        Button shopBTN = (Button) findViewById(R.id.shopBTN);
        Button inventoryBTN = (Button) findViewById(R.id.inventoryBTN);
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                isPlaying = false;
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        nyankoAI.lastPoint(touchX, touchY);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (nyankoAI != null && nyankoAI.isTouched((int) event.getX(), (int) event.getY())) {
                nyankoAI.onTouch();
            }
        }
        Log.e(TAG, "Touch: " + touchX + " " + touchY);
        return true;
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
    public void draw() {
        if(surfaceHolder.getSurface().isValid())
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);
                canvas.drawBitmap(background, 0, 0, null);
            if(nyankoAI != null){
                nyankoAI.draw(canvas);
                displayText(canvas);
                canvas.drawCircle(screenWidth / 2, screenHeight / 2, 50, new Paint());
            }else {
                Log.e(TAG, "NyankoAI is null in draw()");
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void displayText(Canvas canvas){
        NyankoAI.Mood initialMood = nyankoAI.getMood();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("HP: " + nyankoAI.getHP() +"/10", 20, 100, paint);
        canvas.drawText("Hunger: " + nyankoAI.getHunger() +"/10", 20, 150, paint);
        canvas.drawText("Mood: " + initialMood.getMoodString(),20, 200, paint);
    }
    private void control() {
        try{
            Thread.sleep(17);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void pause() {
        isPlaying = false;
        try{
            gameThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
