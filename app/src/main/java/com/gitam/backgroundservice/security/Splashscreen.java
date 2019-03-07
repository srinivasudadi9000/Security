package com.gitam.backgroundservice.security;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitam.backgroundservice.R;

import java.util.ArrayList;

public class Splashscreen extends AppCompatActivity {

    LinearLayout main_ll;
    TextView clickhere_tv, allapps_tv;
    String res;
    ArrayList<AppDetails> appDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        appDetails = new ArrayList<AppDetails>();
         clickhere_tv = findViewById(R.id.clickhere_tv);
         //  allapps_tv = findViewById(R.id.allapps_tv);
        clickhere_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trail = new Intent(Splashscreen.this,Subhome.class);
                startActivity(trail);

                //  allapps_tv.setText(res);
            }
        });
        //https://android--code.blogspot.com/2017/09/android-get-all-apps-permissions.html

        ((Animatable)((ImageView)findViewById(R.id.iv_animated_circle)).getDrawable()).start();




        ImageView aa = findViewById(R.id.heart_rate);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable)((ImageView)findViewById(R.id.heart_rate)).getDrawable()).start();
            }
        });
    }
}
