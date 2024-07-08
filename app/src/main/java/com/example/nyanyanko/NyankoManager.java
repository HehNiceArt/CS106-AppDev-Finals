package com.example.nyanyanko;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NyankoManager {
    private static NyankoAI nyankoAIInstance;

    public static  NyankoAI getInstance(Context context){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.nyanko);
        if(nyankoAIInstance == null){
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
            nyankoAIInstance = new NyankoAI(resized, bitmap.getWidth(), bitmap.getHeight());
        }
        return nyankoAIInstance;
    }
}
