package com.example.nyanyanko;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.PrecomputedText;
import android.util.Log;

import java.util.Random;

public class NyankoAI {
    private long lastMoveTime;
    private Bitmap bitmap;
    private Bitmap scaledBitmap;
    private int x,y;
    private float speedX, speedY;
    private int screenWidth, screenHeight;
    private Random random;
    private final float DEFAULT_SPEED = 10.0f;
    String TAG = "NyankoAI";
    public NyankoAI(Bitmap bitmap, int screenWidth, int screenHeight, float scale)
   {
      this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
      this.x = screenWidth;
      this.y = screenHeight;

      this.screenWidth = screenWidth;
      this.screenHeight = screenHeight;

      this.speedX = DEFAULT_SPEED;
      this.speedY = DEFAULT_SPEED;

      this.random = new Random();
      scaleBitmap(scale);
   }
   public void update(int screenWidth, int screenHeight) {
       x += speedX;
       y += speedY;

       if(x <= 0 || x + bitmap.getWidth() >= screenWidth || y <= 0 || y + bitmap.getHeight() >= screenHeight) {
           //Randomize new movement angle
           double angle = random.nextDouble() * 2 * Math.PI; // Random angle in radians
           speedX = (float) (DEFAULT_SPEED * Math.cos(angle));
           speedY = (float) (DEFAULT_SPEED * Math.sin(angle));
       }
   }
    private void scaleBitmap(float scale) {
        int scaledWidth = (int) (bitmap.getWidth() * scale);
        int scaledHeight = (int) (bitmap.getHeight() * scale);

        // Create a matrix for the scaling transformation
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Apply the transformation to the bitmap
        this.scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
   public void draw(Canvas canvas, float scale){
           canvas.drawBitmap(scaledBitmap, x, y,null);
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

