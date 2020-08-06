package com.example.firstgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    private Heli heli;
    public Score score;
    private obstacle pipe1,pipe2,pipe3;
    public static int gapHeight =300;
    public static int velocity = 5;
    private static int screenHeight=Resources.getSystem().getDisplayMetrics().heightPixels;
    private static int screenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    long curntTime,prevTime;
    Canvas canvas2;
    int obstacleDistance,levelGame;
    private Context mContext;

    public GameView(Context context) {
        super(context);        this.mContext=context;
        obstacleDistance=900;
        levelGame=0;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        heli = new Heli(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter2), 200, 150));
        score=new Score();
        makeLevel();
        curntTime = System.currentTimeMillis();
        prevTime=curntTime;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        heli.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
        findScore();
        logic();

        //increase difficulty

        if(score.Points>=levelGame+50)
        {
            obstacleDistance=obstacleDistance-100;
            levelGame=score.Points;
        }

        //Check whether the obstacles gone left off the screen
        if (pipe1.xX + 250 < 0) {
            Random r = new Random();
            Random r2= new Random();
            float value2=(float)r2.nextInt(41)/100;
            int value1 = r.nextInt(300);
            pipe1.yY= (int)((value2+0.55)*screenHeight);
            pipe1.xX = pipe3.xX + value1 +obstacleDistance;
        }

        if (pipe2.xX + 250 < 0) {
            Random r = new Random();
            Random r2= new Random();
            float value2=(float)r2.nextInt(41)/100;
            int value1 = r.nextInt(300);
            pipe2.yY= (int)((value2+0.55)*screenHeight);
            pipe2.xX = pipe1.xX + value1 + obstacleDistance;
        }

        if (pipe3.xX + 250 < 0) {
            Random r = new Random();
            Random r2= new Random();
            float value2=(float)r2.nextInt(41)/100;
            int value1 = r.nextInt(300);
            pipe3.yY= (int)((value2+0.55)*screenHeight);
            pipe3.xX = pipe2.xX + value1 + obstacleDistance;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawRGB(255, 255, 255);
            heli.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
            score.draw(canvas);
            canvas2=canvas; //to use in endGame

            if(!thread.isRunning())
            {
                Intent intent=new Intent(mContext,EndGame.class);
                intent.putExtra("score",score.Points);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        heli.y=heli.y-heli.yVelocity*20;
        return super.onTouchEvent(event);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void makeLevel()
    {
        Bitmap bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.down), 250, screenHeight);
        Bitmap bmp2 = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.up), 250, screenHeight);

        pipe1 = new obstacle(bmp, bmp2, 1000, (int) (screenHeight*0.8));
        pipe2 = new obstacle(bmp, bmp2, 2000, (int) (screenHeight*0.6));
        pipe3 = new obstacle(bmp, bmp2, 3000, (int) (screenHeight*0.7));

    }

    public void logic()
    {
        if (heli.y < -pipe1.yY + screenHeight && heli.x + 200 > pipe1.xX && heli.x < pipe1.xX + 250)
        { endGame(); }

        if (heli.y < -pipe2.yY + screenHeight && heli.x + 200 > pipe2.xX && heli.x < pipe2.xX + 250)
        { endGame(); }

        if (heli.y < -pipe3.yY + screenHeight && heli.x + 200 > pipe3.xX && heli.x < pipe3.xX + 250)
        { endGame(); }

        if (heli.y + 150 > screenHeight+gapHeight-pipe1.yY && heli.x + 200 > pipe1.xX && heli.x < pipe1.xX + 250)
        { endGame(); }

        if (heli.y + 150 > gapHeight- pipe2.yY+screenHeight && heli.x + 200 > pipe2.xX && heli.x < pipe2.xX + 250)
        { endGame(); }

        if (heli.y + 150 > gapHeight- pipe3.yY+screenHeight && heli.x + 200 > pipe3.xX && heli.x < pipe3.xX + 250)
        { endGame(); }

        //Detect if the character has gone off the bottom or top of the screen

        if (heli.y < 0) {
            endGame(); }
        if (heli.y+150 > screenHeight) {
            endGame(); }
    }

    public  void findScore()
    {
        curntTime = System.currentTimeMillis();
        int elapsedTime=(int)((curntTime-prevTime)/1000);

        if(elapsedTime>=1)
        {
            prevTime=curntTime;
            score.update();
        }
    }

    public void endGame()
    {
        MainActivity.music.stop();
        if(MainActivity.sound)
            MainActivity.music_crash.start();

        heli.change(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.helicrash), 200, 190));
        heli.draw(canvas2);
        thread.setRunning(false);

    }

}
