package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread = null;
    private boolean isPlaying;
    private SurfaceHolder surfaceHolder;
    private Bitmap background;
    private  Paint paint;
    private Paint backgroundPaint;
    private Paint borderPaint;

    private int hp = 10;
    private int hunger = 10;
    private String mood = "Good";
    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
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

    }
    public void draw()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(background, 0, 0, paint);
            stats(canvas);
            uiButtons(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void stats(Canvas canvas)
    {
        int left = 30;
        int top = 50;
        int right = 400;
        int bottom = 250;
        canvas.drawRect(left, top, right, bottom, backgroundPaint);
        canvas.drawRect(left, top, right, bottom, borderPaint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("HP: " + hp + "/10", left + 20, top + 50, paint);
        canvas.drawText("Hunger: " + hunger + "/10", left + 20, top + 110, paint);
        canvas.drawText("Mood: " + mood,left + 20, top+ 170, paint);

    }
    public void uiButtons(Canvas canvas)
    {
        int left = 600;
        int top = 50;
        int right = 1010;
        int bottom = 250;
        canvas.drawRect(left, top,right, bottom, backgroundPaint);
        canvas.drawRect(left, top,right, bottom, borderPaint);
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
}
