package com.example.nyanyanko;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class NyankoAI {

    //Hello layout branch!!

    private Gameplay gameplay;
    private Bitmap bitmap;
    private int x,y;
    private float speedX, speedY;
    private int screenWidth, screenHeight;
    private Random random;
    private final float DEFAULT_SPEED = 3.0f;
    String TAG = "NyankoAI";

    private long lastStateChangeTime;
    private long hpTime;
    private long hungerTime;
    private final long WALKING_DURATION = 4000;
    private final long IDLE_DURATION = 7000;
    private long statePauseTime;
    private boolean isStatePaused;

    private int targetX, targetY;

    private enum State{
        WALKING,
        IDLE,
        PLAYFUL
    }
    private State currentState;
    private State previousState;

    public int hunger = 10;
    public int hp = 10;
    public enum Mood{
        DEFAULT("GOOD"),
        SAD("SAD"),
        ANGRY("ANGRY");
        private final String moodString;

        Mood(String moodString) {
            this.moodString = moodString;
        }

        public String getMoodString() {
            return moodString;
        }
    }
    private Mood currentMood = Mood.DEFAULT;
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
      this.isStatePaused = false;

      this.hpTime = System.currentTimeMillis();
      this.hungerTime = System.currentTimeMillis();
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
        } else if (currentState == State.PLAYFUL) {
           moveToTarget();
        }

        decreaseHunger(currentTime);
        checkHealth();
        checkMood(currentTime);
   }
   private void decreaseHunger(long currentTime){
        if(currentTime - hungerTime> 1000){
            hunger--;
            if(hunger >= 0){
                hungerTime= currentTime;
            }else{
                hunger = 0;
            }
        }
   }
    private void decreaseHP(long currentTime){
        if(currentTime - hpTime> 1000){
            hp--;
            if(hp>= 0){
                hpTime= currentTime;
            }else{
                hp = 0;
            }
        }
    }
   private void checkHealth(){
        if(hp <= 0){
            //have a pop up and say game over
        }
   }
   private void checkMood(long currentTIme){
        if(hunger <= 5 && hunger >= 3){
            decreaseHP(currentTIme);
            setMood(Mood.SAD);
        } else if (hunger <= 3 && hunger >= 0 ){
            decreaseHP(currentTIme);
           setMood(Mood.ANGRY);
        }
   }

    private void switchState(){
        if(currentState == State.WALKING){
            currentState = State.IDLE;
        }else if (currentState == State.IDLE){
            currentState = State.WALKING;
            changeWalkingDirection();
        } else if (currentState == State.PLAYFUL) {
            currentState = previousState == State.IDLE ? State.PLAYFUL : State.WALKING;
            if(currentState == State.WALKING){
                changeWalkingDirection();
            }
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
   private void moveToTarget(){
       float deltaX = targetX - x;
       float deltaY = targetY - y;
       double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

       Log.e(TAG, "Dist" + distance);

       if(distance > DEFAULT_SPEED){
           speedX = (float) (DEFAULT_SPEED * (deltaX / distance));
           speedY = (float) (DEFAULT_SPEED * (deltaY / distance));
           x += speedX;
           y += speedY;
       }else {
           x = targetX;
           y = targetY;

           isStatePaused = false;
           lastStateChangeTime = System.currentTimeMillis();
           currentState = State.IDLE;
       }
   }
   public boolean isTouched(int touchX, int touchY){
       return touchX >= x && touchX <= (x + bitmap.getWidth()) && touchY >= y && touchY <= (y + bitmap.getHeight());
   }
    public void lastPoint(int touchX, int touchY){
        targetX = touchX;
        targetY = touchY;
    }

    public void onTouch(){
        if (currentState != State.PLAYFUL) {
            previousState = currentState;
            currentState = State.PLAYFUL;
            isStatePaused = true;
            statePauseTime = System.currentTimeMillis();
        } else {
            currentState = previousState;
            isStatePaused = false;
            lastStateChangeTime = System.currentTimeMillis() - (statePauseTime - lastStateChangeTime); // Adjust state change time
        }
    }
   public void draw(Canvas canvas){
           canvas.drawBitmap(bitmap, x, y,new Paint());
   }

   public int getHunger(){
        return hunger;
   }
   public int getHP(){
        return hp;
   }
   public Mood getMood(){
        return currentMood;
   }
    public void setMood(Mood mood) {
        this.currentMood = mood;
    }
}

