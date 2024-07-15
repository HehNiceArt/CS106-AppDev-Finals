package com.example.nyanyanko;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.widget.ImageView;


public class ImageDialog extends Dialog {
    private Context context;
    private Bitmap imageResource;

    public ImageDialog(Context context, Bitmap imageResource) {
        super(context);
        this.context = context;
        this.imageResource = imageResource;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_image_layout);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(imageResource);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                Log.d("ImageDialog", "dismissing image");
            }
        }, 1000);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        return true;
    }
}
