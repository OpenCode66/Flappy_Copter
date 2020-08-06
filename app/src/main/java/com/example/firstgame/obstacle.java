package com.example.firstgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

////Class for Obstacles to implement it as an object
public class obstacle {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public obstacle (Bitmap bmp, Bitmap bmp2, int x, int y) {
        image = bmp;
        image2 = bmp2;
        yY = y;
        xX = x;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xX, -yY, null);
        canvas.drawBitmap(image2,xX, (screenHeight)+(GameView.gapHeight-yY), null);

    }
    public void update() {
        xX -= GameView.velocity;
    }



}
