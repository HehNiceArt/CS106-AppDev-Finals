package com.example.nyanyanko;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.PrecomputedText;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;
import java.util.logging.Handler;

public class NyankoAI {
    private Bitmap bitmap;
    private int x,y;
    private float speedX, speedY;
    private int screenWidth, screenHeight;
    private Random random;
    private final float DEFAULT_SPEED = 3.0f;
    String TAG = "NyankoAI";

    private final long STATE_DURATION = 5000;
    private long lastStateChangeTime;
    private final long WALKING_DURATION = 4000;
    private final long IDLE_DURATION = 7000;

    private enum State{
        WALKING,
        IDLE
    }
    private State currentState;

    public NyankoAI(Bitmap bitmap, int screenWidth, int screenHeight, float scale)
   {
      this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
      this.x = screenWidth;
      this.y = screenHeight;

      this.screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
      this.screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

      this.speedX = DEFAULT_SPEED;
      this.speedY = DEFAULT_SPEED;

      this.random = new Random();

      this.currentState = State.WALKING;
      this.lastStateChangeTime = System.currentTimeMillis();
   }

   public void update(){
        long currentTime = System.currentTimeMillis();
        long stateDuration = (currentState == State.WALKING) ? WALKING_DURATION : IDLE_DURATION;
        if(currentTime - lastStateChangeTime > stateDuration){
            switchState();
            lastStateChangeTime = currentTime;
        }
        if(currentState == State.WALKING){
            walking();
        }
   }
    private void switchState(){
        if(currentState == State.WALKING){
            currentState = State.IDLE;
        }else{
            currentState = State.WALKING;
            changeWalkingDirection();
        }
    }
    private void changeWalkingDirection(){
        double angle = random.nextDouble() * 2 * Math.PI;
        speedX = (float) (DEFAULT_SPEED* Math.cos(angle));
        speedY = (float) (DEFAULT_SPEED* Math.sin(angle));
    }

   public void walking(){
       x += speedX;
       y += speedY;

       if(x <= 0 || x + bitmap.getWidth() >= screenWidth){
           speedX = -speedX;
       }
       if(y <= 0 || y + bitmap.getHeight()>= screenHeight){
           speedY = -speedY;
       }
   }
   public void idle(){
        //do nothing
   }
   public void draw(Canvas canvas){
           canvas.drawBitmap(bitmap, x, y,new Paint());
   }
   public void setSpeed(float speedX, float speedY){
       this.speedX = speedX;
       this.speedY = speedY;
   }
   public int getX(){
       return x;
   }
   public int getY(){
       return y;
   }
    public int getWidth() {
        return bitmap.getWidth();
    }
    public int getHeight(){
        return bitmap.getHeight();
    }
    public float getSpeedX(){
        return speedX;
    }
    public float getSpeedY(){
       return speedY;
    }
}

