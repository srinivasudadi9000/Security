package com.gitam.backgroundservice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Intent mServiceIntent;
    SensorService mSensorService;

    Button notification_btn;

    Context ctx;

    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
       // mServiceIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        givepermissionaccess();


        notification_btn = findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Happy new year dadi",Toast.LENGTH_SHORT).show();
                if (!isMyServiceRunning(mSensorService.getClass())) {
                    startService(mServiceIntent);
                }
            }
        });
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }


    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        super.onDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
            givepermissionaccess();
            //  Toast.makeText(getBaseContext(), "Resume Task needing this permission", Toast.LENGTH_SHORT).show();
        } else {
            //finish();
            Toast.makeText(getBaseContext(), "you can not use this application without givivng access to ur location Thanks!!", Toast.LENGTH_SHORT).show();
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
        }

       /* final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i = 0; i < recentTasks.size(); i++)
        {
            Log.d("Executed app", "Application executed : " +
                    recentTasks.get(i).baseActivity.toShortString()+ "\t\t ID: "+recentTasks.get(i).id+"");
            if (recentTasks.get(i).baseActivity.toShortString().contains("com.srinivas.backgroundservice")){

                ActivityManager ac = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningServiceInfo> services = ac.getRunningServices(Integer.MAX_VALUE);

                long currentMillis = Calendar.getInstance().getTimeInMillis();
                Calendar cal = Calendar.getInstance();


                for (ActivityManager.RunningServiceInfo info : services) {


                    cal.setTimeInMillis(currentMillis-info.activeSince);

                    //if (info.process.contains("com.srinivas.backgroundservice"))
                    Log.i("Dadi HERE", String.format("Process %s has been running since: %d ms",info.process,  info.activeSince));




                }


            }
        }*/
    }

    public void givepermissionaccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WAKE_LOCK}, 0);
        } else {
            Toast.makeText(getBaseContext(), "All permissions granted.", Toast.LENGTH_SHORT).show();
//            givepermissionaccess();
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }

        }

    }


}
