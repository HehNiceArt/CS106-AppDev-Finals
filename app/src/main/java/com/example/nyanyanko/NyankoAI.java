package com.example.nyanyanko;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NyankoAI{
    private Bitmap bitmap;
    private int x, y;
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

    private enum State {
        WALKING,
        IDLE,
        PLAYFUL
    }

    private State currentState;
    private State previousState;

    public int hunger = 10;
    public int hp = 10;

    public enum Mood {
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
    private List<InventoryItem> inventory;
    private List<ToyItem> toy;
    Context mcontext;

    public NyankoAI(Context context, Bitmap bitmap, int screenWidth, int screenHeight) {
        this.mcontext = context;
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

        this.inventory = new ArrayList<>();
        this.toy = new ArrayList<>();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long stateDuration = (currentState == State.WALKING) ? WALKING_DURATION : IDLE_DURATION;
        if (currentTime - lastStateChangeTime > stateDuration) {
            switchState();
            lastStateChangeTime = currentTime;
        }
        if (currentState == State.WALKING) {
            walking();
        } else if (currentState == State.PLAYFUL) {
            moveToTarget();
        }

        decreaseHunger(currentTime);
        checkHP();
        checkMood(currentTime);
    }

    //region Inventory
    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void addItemToInventory(InventoryItem item) {
        for (InventoryItem invItem : inventory) {
            if (invItem.getName().equals(item.getName())) {
                invItem.setQuantity(invItem.getQuantity() + 1);
                Log.d(TAG, "Update quantity of " + item.getName());
                return;
            }
        }
        inventory.add(item);
        Log.d(TAG, "Add item " + item.getName());
    }

    public List<ToyItem> getToy() {
        return toy;
    }

    public void addItemToToy(ToyItem item) {
        for (ToyItem toyItem : toy) {
            if (toyItem.getName().equals(item.getName())) {
                toyItem.setQuantity((toyItem.getQuantity() + 1));
                return;
            }
        }
        toy.add(item);
    }

    //endregion
    //region Stats
    private void decreaseHunger(long currentTime) {
        if (currentTime - hungerTime > 60000) {
            hunger--;
            if (hunger >= 0) {
                hungerTime = currentTime;
            } else {
                hunger = 0;
            }
        }
    }

    private void decreaseHP(long currentTime) {
        if (currentTime - hpTime > 120000) {
            hp--;
            if (hp >= 0) {
                hpTime = currentTime;
            } else {
                hp = 0;
            }
        }
    }

    private void checkMood(long currentTIme) {
        if (hunger <= 5 && hunger >= 3) {
            decreaseHP(currentTIme);
            setMood(Mood.SAD);
        } else if (hunger <= 3 && hunger >= 0) {
            decreaseHP(currentTIme);
            setMood(Mood.ANGRY);
        } else{
            setMood(Mood.DEFAULT);
        }
    }

    //endregion
    //region Behavior trees
    private void switchState() {
        if (currentState == State.WALKING) {
            currentState = State.IDLE;
        } else if (currentState == State.IDLE) {
            currentState = State.WALKING;
            changeWalkingDirection();
        } else if (currentState == State.PLAYFUL) {
            currentState = previousState == State.IDLE ? State.PLAYFUL : State.WALKING;
            if (currentState == State.WALKING) {
                changeWalkingDirection();
            }
        }
    }

    private void changeWalkingDirection() {
        double angle = random.nextDouble() * 2 * Math.PI;
        speedX = (float) (DEFAULT_SPEED * Math.cos(angle));
        speedY = (float) (DEFAULT_SPEED * Math.sin(angle));
    }

    public void walking() {
        x += speedX;
        y += speedY;

        if (x <= 0 || x + bitmap.getWidth() >= screenWidth) {
            speedX = -speedX;
        }
        if (y <= 0 || y + bitmap.getHeight() >= screenHeight) {
            speedY = -speedY;
        }
    }

    private void moveToTarget() {
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        Log.e(TAG, "Dist" + distance);

        if (distance > DEFAULT_SPEED) {
            speedX = (float) (DEFAULT_SPEED * (deltaX / distance) );
            speedY = (float) (DEFAULT_SPEED * (deltaY / distance) );
            x += speedX;
            y += speedY;
        } else {
            x = targetX;
            y = targetY;

            isStatePaused = false;
            lastStateChangeTime = System.currentTimeMillis();
            currentState = State.IDLE;
        }
    }

    public boolean isTouched(int touchX, int touchY) {
        return touchX >= x && touchX <= (x + bitmap.getWidth()) && touchY >= y && touchY <= (y + bitmap.getHeight());
    }

    public void lastPoint(int touchX, int touchY) {
        targetX = touchX;
        targetY = touchY;
    }

    public void onTouch() {
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

    //endregion
    //region Income
    //When playing with Nyanko
    public void incomeBonus(){

    }
    public void toyBonus(){

    }
    //endregion
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, new Paint());
    }
    public int getHunger() {
        return hunger;
    }
    public void fillHunger(int hungerChange){
        int currentHunger = hunger;
       currentHunger += hungerChange;
       if(currentHunger > 10){
       }else {
           hunger = currentHunger;
       }
    }
    public void checkHP(){
        if(hp <= 0){
            showGameOverDialog();
            Log.d(TAG, "Showing game over dialog");
        }
    }
    public void fillHP(int hpChange){
        int currentHP = hp;
        currentHP += hpChange;
        if(currentHP > 10){
        }else{
            hp = currentHP;
        }
    }
    private void showGameOverDialog() {
        ((Activity) mcontext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("Game Over");
                builder.setMessage("Nyanko's health has dropped to 0. Game over!");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public int getHP() {
        return hp;
    }

    public Mood getMood() {
        return currentMood;
    }

    public void setMood(Mood mood) {
        this.currentMood = mood;
    }
}

