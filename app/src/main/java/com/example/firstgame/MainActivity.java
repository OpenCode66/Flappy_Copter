package com.example.firstgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

//Here Game View is implemented
public class MainActivity extends Activity {
    public static MediaPlayer music, music2,music_crash;
    ImageButton pause;
    public static boolean sound=true;
    GameView gameView;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout game = new FrameLayout(this);
        gameView = new GameView(this);

        SharedPreferences data =getSharedPreferences("data", Context.MODE_PRIVATE);
        if(data.contains("sound"))
            if(data.getString("sound","").equals("0"))
                sound=false;

        music = MediaPlayer.create(this, R.raw.music);
        music2 = MediaPlayer.create(this, R.raw.click);
        music_crash=MediaPlayer.create(this,R.raw.crash);
        music_crash.setVolume(0.5f,0.5f);
        if(sound)
            music.start();
        music.setLooping(true);


        pause = new ImageButton(this);
        pause.setImageResource(R.drawable.ic_action_pause);
        pause.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        pause.setX(Resources.getSystem().getDisplayMetrics().widthPixels - 150);
        pause.setY(10);

        game.addView(gameView);
        game.addView(pause);

        setContentView(game);  //game started

        //when pause button is clicked
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound)
                    music2.start();
                music.pause();
                gameView.thread.pause = true;

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.pause, null);
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                // show the popup window
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                ImageButton back = popupView.findViewById(R.id.resume);
                ImageButton home = popupView.findViewById(R.id.home);
                ImageButton restart = popupView.findViewById(R.id.restart);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound)
                            music2.start();
                        if(sound)
                            music.start();
                        popupWindow.dismiss();
                        gameView.thread.pause = false;

                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound)
                            music2.start();
                        Intent intent = new Intent(MainActivity.this, home.class);
                        startActivity(intent);
                        finish();
                    }
                });
                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound)
                            music2.start();
                        popupWindow.dismiss();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sound)
            music.start();
    }

    @Override
    public void onBackPressed() {
        pause.performClick();
    }
}