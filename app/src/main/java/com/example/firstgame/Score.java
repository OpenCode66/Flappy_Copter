package com.example.firstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import androidx.annotation.NonNull;

public class Score {
    public int x, y;
    public int Points;

    public Score() {
        x = 20;
        y = 60;
        Points=0;
    }

    public void draw(Canvas canvas) {
        Paint paint= new TextPaint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);

        Paint bkgPaint=new Paint();
        bkgPaint.setColor(Color.BLACK);

        Rect background = getTextBackgroundSize(x, y, "Score: "+ Points, paint);
        canvas.drawRect(background, bkgPaint);
        canvas.drawText("Score: "+ Points, x, y, paint);

    }

    public void update() {
        Points++;
    }

    private @NonNull Rect getTextBackgroundSize(float x, float y, @NonNull String text, @NonNull Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float halfTextLength = paint.measureText(text) / 2 ;
        return new Rect((int) (x-10), (int) (y + fontMetrics.top), (int) (x + halfTextLength*2+10), (int) (y + fontMetrics.bottom));
    }
}