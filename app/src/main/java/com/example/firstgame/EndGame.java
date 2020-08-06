package com.example.firstgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends AppCompatActivity {
    String HighScore="0";
    boolean sound=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        SharedPreferences data=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=data.edit();
        if(data.contains("sound"))
            if(data.getString("sound","").equals("0"))
                sound=false;
        if(data.contains("highscore"))
            HighScore=data.getString("highscore","");


        Intent intent=getIntent();
        int score=intent.getIntExtra("score",0);

        final MediaPlayer music2 = MediaPlayer.create(this,R.raw.click);

        ImageButton home=findViewById(R.id.home);
        ImageButton restart=findViewById(R.id.restart);
        TextView playerScore=findViewById(R.id.score);
        TextView newHighScore=findViewById(R.id.highscore);

        playerScore.setText(String.valueOf(score));

        if(score>(int)Float.parseFloat(HighScore))
        {
            newHighScore.setText("New Highscore");
            editor.putString("highscore",String.valueOf(score));
            editor.commit();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound)
                    music2.start();
                Intent intent=new Intent(EndGame.this,home.class);
                startActivity(intent);
                finish();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound)
                    music2.start();
                Intent intent=new Intent(EndGame.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Exit Game");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}