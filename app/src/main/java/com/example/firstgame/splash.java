package com.example.firstgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class splash extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView title = findViewById(R.id.imageView);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this, home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout2);
                finish();
            }
        },5000);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        title.startAnimation(myanim);
    }


}

