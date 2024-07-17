package com.example.nyanyanko;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class NyankoManager {
    private static NyankoAI nyankoAIInstance;

    public static  NyankoAI getInstance(Context context,Bitmap bitmap, ImageView imageView){
        if(nyankoAIInstance == null){
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
            nyankoAIInstance = new NyankoAI(context, resized, resized.getWidth(), resized.getHeight(), imageView);
            Log.d("Manager", "fuckinnng shitts");
        }
        return nyankoAIInstance;
    }
    public static NyankoAI getExistingInstance(){
        Log.d("Manager", nyankoAIInstance.toString());
        return nyankoAIInstance;
    }
    public static void releaseInstance(){
        nyankoAIInstance = null;
    }
}
