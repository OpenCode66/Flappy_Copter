package com.example.firstgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

//Class for helicopter to implement it as an object
public class Heli {
    private Bitmap image;
    public int x,y;
    public int yVelocity = 3;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Heli(Bitmap bmp) {
        image = bmp;
        x=150;
        y=100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
    public void update()
    {
        y += yVelocity;
    }

    public void change(Bitmap bmp) {
        image = bmp;
    }
}
