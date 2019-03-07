package com.gitam.backgroundservice.security;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.gitam.backgroundservice.R;

public class Subhome extends Activity implements View.OnClickListener {
    CardView clicktoscan, harmfulfiles, apkotherfiles, installedapps;
    SharedPreferences.Editor shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subhome);

        installedapps = findViewById(R.id.installedapps);
        apkotherfiles = findViewById(R.id.apkotherfiles);
        harmfulfiles = findViewById(R.id.harmfulfiles);
        clicktoscan = findViewById(R.id.clicktoscan);


        installedapps.setOnClickListener(this);
        apkotherfiles.setOnClickListener(this);
        harmfulfiles.setOnClickListener(this);
        clicktoscan.setOnClickListener(this);

        shared = getSharedPreferences("options", MODE_PRIVATE).edit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clicktoscan:
                shared.putString("option", "clicktoscan");
                shared.commit();
                Intent trail = new Intent(Subhome.this, Scanning.class);
                startActivity(trail);
                break;
            case R.id.apkotherfiles:

                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                break;
            case R.id.harmfulfiles:
                shared.putString("option", "harmfulfiles");
                shared.commit();
                Intent harmfull = new Intent(Subhome.this, Trail.class);
                startActivity(harmfull);
                break;
            case R.id.installedapps:
                shared.putString("option", "installedapps");
                shared.commit();
                Intent installed = new Intent(Subhome.this, Trail.class);
                startActivity(installed);
                break;
        }
    }
}
