package com.example.nyanyanko;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.nyanyanko.ShopAct.ShopActivity;

import java.util.logging.Handler;


public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

    private static final String TAG = "GameView";
    private NyankoAI nyankoAI;
    private Gameplay gameplay;
    private ShopActivity shopActivity;
    private Thread gameThread = null;
    private boolean isPlaying;
    private SurfaceHolder surfaceHolder;
    private Bitmap background;
    private int screenWidth;
    private int screenHeight;
    private Handler handler;
    private String petName;

    public boolean isPaused;
    Bitmap bit;
    public GameView(Context context, String petName){
        super(context);
        surfaceHolder = getHolder();
        this.petName = petName;

        shopActivity = new ShopActivity();
        surfaceCreated(surfaceHolder);
        surfaceHolder.addCallback(this);

        setFocusable(true);
    }
    ImageView imageView;

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        resume();
        screenWidth = getWidth();
        screenHeight = getHeight();
    }
    public void init(ImageView imageView){
        background = BitmapFactory.decodeResource(getResources(), R.drawable.gameviewbg);
        gameplay = new Gameplay();

        this.imageView = imageView;

        bit = BitmapFactory.decodeResource(getResources(), R.drawable.walking_default_right);

        if(this.imageView != null) {
            Glide.with(this).asGif().load(R.drawable.walking_default_right).into(this.imageView);
            nyankoAI = NyankoManager.getInstance(getContext());
            Log.d("GameVIew", "NyankoAI calling nyankoManager!");
        }
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
        return true;
    }
    @Override
    public void run()
    {
        while(isPlaying) {
            while(!isPaused) {
                update();
                draw();
                control();
            }
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
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            background = Bitmap.createScaledBitmap(background, screenWidth , screenHeight, false);
                canvas.drawBitmap(background, 0, 0, null);
            if(nyankoAI != null){
                nyankoAI.draw();
                displayText(canvas);
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
        int _x = 20;
        int _y = 200;
        canvas.drawText(petName +"", _x, _y, paint);
        canvas.drawText("HP: " + nyankoAI.getHP() +"/10", _x, _y + 50, paint);
        canvas.drawText("Hunger: " + nyankoAI.getHunger() +"/10", _x, _y + 100, paint);
        canvas.drawText("Mood: " + initialMood.getMoodString(),_x, _y + 150, paint);
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
        isPaused = false;
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
    public void setPause(boolean paused){
        this.isPaused = paused;
    }
}
