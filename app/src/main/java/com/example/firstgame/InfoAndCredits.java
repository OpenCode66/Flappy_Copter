package com.example.firstgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoAndCredits extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);



        TextView linkGit=findViewById(R.id.linkgit);
        TextView feedback=findViewById(R.id.feedback);
        TextView removeAd=findViewById(R.id.removeAd);
        ImageButton back=findViewById(R.id.imageButton3);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoAndCredits.this,home.class);
                startActivity(intent);
                finish();
            }
        });
        linkGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","navaneethvattakkayam@gmail.com", null));
                intentMail.putExtra(Intent.EXTRA_EMAIL, "navaneethvattakkayam@gmail.com");
                intentMail.putExtra(Intent.EXTRA_SUBJECT, "Flappy Copter");
                startActivity(intentMail);
            }
        });
        removeAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Kindly turn off your Data :)",Toast.LENGTH_SHORT).show();
            }
        });
    }
}