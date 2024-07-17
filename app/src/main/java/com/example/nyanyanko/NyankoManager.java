package com.example.nyanyanko;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

public class NyankoManager {
    private static NyankoAI nyankoAIInstance;

    public static  NyankoAI getInstance(Context context, Bitmap bitmap, ImageView imageView){
        if(nyankoAIInstance == null){
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
            nyankoAIInstance = new NyankoAI(context, resized, resized.getWidth(), resized.getHeight(), imageView);
        }
        return nyankoAIInstance;
    }
    public static NyankoAI getExistingInstance() {
        return nyankoAIInstance;
    }
    public static void releaseInstance(){
        nyankoAIInstance = null;
    }
}
