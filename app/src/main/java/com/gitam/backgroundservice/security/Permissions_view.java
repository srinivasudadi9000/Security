package com.gitam.backgroundservice.security;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitam.backgroundservice.R;

public class Permissions_view extends Activity {
    TextView permissions_tv;
    ImageView harm_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_view);

        permissions_tv = findViewById(R.id.permissions_tv);
        harm_img = findViewById(R.id.harm_img);
        SharedPreferences ss = getSharedPreferences("options", MODE_PRIVATE);
        permissions_tv.setText(getPermissionsByPackageName(getIntent().getStringExtra("packagename")));
        if (ss.getString("option", "").equals("harmfulfiles")) {

            if (permissions_tv.getText().toString().contains("ACCESS_COARSE_LOCATION") &&
                    permissions_tv.getText().toString().contains("ACCESS_FINE_LOCATION") &&
                    permissions_tv.getText().toString().contains("CALL_PHONE") ||
                    permissions_tv.getText().toString().contains("READ_PHONE_STATE") ||
                    permissions_tv.getText().toString().contains("READ_CALL_LOG")) {
                // permissions_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                harm_img.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("NewApi")
    protected String getPermissionsByPackageName(String packageName) {
        // Initialize a new string builder instance
        StringBuilder builder = new StringBuilder();
        try {
            // Get the package info
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            // Permissions counter
            int counter = 1;
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    String permission = packageInfo.requestedPermissions[i];
                    // To make permission name shorter
                    //permission = permission.substring(permission.lastIndexOf(".")+1);
                    builder.append("" + counter + ". " + permission + "\n\n");
                    System.out.println("permissions check please find dadi " + "appname " + packageName + "  " + permission);
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
