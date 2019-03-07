package com.gitam.backgroundservice.security;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gitam.backgroundservice.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trail extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private Spinner mSpinner;
    private TextView mTextView;
    ImageView appicon_img;
    ArrayList<Icons> icons;
    Group_Adapter group_adapter;
    RecyclerView app_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = Trail.this;

        // Get the widgets reference from xml layout
        mSpinner = findViewById(R.id.spinner);
        mTextView = findViewById(R.id.text_view);
        appicon_img = findViewById(R.id.appicon_img);
        app_rv = findViewById(R.id.app_rv);
        icons = new ArrayList<>();
        // Make text view scrollable
        //  mTextView.setMovementMethod(new ScrollingMovementMethod());
        getInstalledPackages();

        group_adapter = new Group_Adapter(Trail.this,icons,R.layout.appdetails_single);

        app_rv.setLayoutManager(new LinearLayoutManager(Trail.this));
        app_rv.setAdapter(group_adapter);
       /* // Get the installed package list
        HashMap<String, String> map = getInstalledPackages();

        // Get the values array from hash map
        final String[] values = map.values().toArray(new String[map.size()]);
        final String[] keys = map.keySet().toArray(new String[map.size()]);

        // Initialize a new array adapter instance
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mActivity,
                android.R.layout.simple_spinner_item,
                values
        );

        // Set the drop down view resource for array adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Data bind the spinner with array adapter
        mSpinner.setAdapter(adapter);

        // Set an item selected listener for the spinner object
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String packageName = keys[i];
                String label = values[i];
                mTextView.setText(label + "\n" + packageName + "\n\n");
                // Display the selected package granted permissions list
                mTextView.append(getPermissionsByPackageName(packageName));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterViesw) {

            }
        });*/
    }


    // Custom method to get all installed package names
    protected void getInstalledPackages() {
        PackageManager packageManager = getPackageManager();

        // Initialize a new intent
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        // Set the intent category
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Set the intent flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        // Initialize a new list of resolve info
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

        // Initialize a new hash map of package names and application label
        HashMap<String, String> map = new HashMap<>();

        // Loop through the resolve info list
        for (ResolveInfo resolveInfo : resolveInfoList) {
            // Get the activity info from resolve info
            ActivityInfo activityInfo = resolveInfo.activityInfo;

            // Get the package name
            String packageName = activityInfo.applicationInfo.packageName;

            // Get the application label
            String label = (String) packageManager.getApplicationLabel(activityInfo.applicationInfo);
            Drawable icon = null;
            try {
                icon = packageManager.getApplicationIcon(packageName);
                appicon_img.setImageDrawable(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            // Put the package name and application label to hash map
            //map.put(packageName, label);
            icons.add(new Icons(packageName, label, icon));
        }

        //return map;
    }


    // Custom method to get app requested and granted permissions from package name
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
                    builder.append("" + counter + ". " + permission + "\n");
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}