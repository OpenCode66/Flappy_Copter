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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

//Home page of game
public class home extends AppCompatActivity {


    public static MediaPlayer music,music2;
    boolean sound=true;
    SharedPreferences data;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        data = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=data.edit();
        if(data.contains("sound"))
            if(data.getString("sound","").equals("0"))
                sound=false;

        TextView textView=findViewById(R.id.textView);
        if(data.contains("highscore"))
            textView.setText("HIGHSCORE: "+data.getString("highscore",""));

        music = MediaPlayer.create(this,R.raw.home_music);
        music2 = MediaPlayer.create(this, R.raw.click);
        music.setLooping(true);
        if(sound)
            music.start();


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

    //when play button is clicked
    public void startGame(View v)
    {
        Intent intent=new Intent(home.this,MainActivity.class);
        music.stop();
        startActivity(intent);
        finish();
    }

    //when info button in clicked
    public void showInfo(View v)
    {
        Intent intent=new Intent(home.this,InfoAndCredits.class);
        music.stop();
        startActivity(intent);
    }

    //when settings button clicked
    public void settings(View view) {
        if (sound)
            music2.start();

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.settings, null);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ImageButton back = popupView.findViewById(R.id.imageButton);
        Switch soundSetting = popupView.findViewById(R.id.switch1);


        soundSetting.setChecked(sound);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound)
                    music2.start();
                popupWindow.dismiss();
            }
        });

        soundSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    music2.start();
                    music.start();
                    sound = true;
                    editor.putString("sound", "1");
                    editor.commit();
                } else {
                    sound = false;
                    music.pause();
                    editor.putString("sound", "0");
                    editor.commit();
                }
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