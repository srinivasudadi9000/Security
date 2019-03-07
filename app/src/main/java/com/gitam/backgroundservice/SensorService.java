package com.gitam.backgroundservice;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SensorService extends Service {
    public int counter=0;
    public Context context;
    public SensorService(Context applicationContext) {
        super();
        this.context = applicationContext;
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        GPSTracker gpsTracker = new GPSTracker(getBaseContext());
        System.out.println("dadi tracking "+gpsTracker.getLongitude()+" longi "+gpsTracker.getLatitude());
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 0, 30000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */

    public void getURLRegister(Double lat,Double longitude) throws IOException {

        @SuppressLint("InvalidWakeLockTag")
        final PowerManager.WakeLock screenLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();

//later


        // avoid creating several instances, should be singleon
        OkHttpClient client = new OkHttpClient();

        //  HttpUrl.Builder urlBuilder = HttpUrl.parse("http://visakhadairy.in/trackernew.asmx/UpdateLocationNew?").newBuilder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://125.62.194.181/vdnew/trackernew.asmx/UpdateLocationNew?").newBuilder();
        urlBuilder.addQueryParameter("Token", "VVD@14");
        urlBuilder.addQueryParameter("DeviceID", "2");
        // urlBuilder.addQueryParameter("DeviceID", "15");
        // urlBuilder.addQueryParameter("DeviceID", "12");
        urlBuilder.addQueryParameter("Lat", String.valueOf(lat));
        urlBuilder.addQueryParameter("Long", String.valueOf(longitude));
        urlBuilder.addQueryParameter("Altitude", "2");
        urlBuilder.addQueryParameter("Speed", "20");
        urlBuilder.addQueryParameter("Course", "0");
        urlBuilder.addQueryParameter("Battery", "10");
        urlBuilder.addQueryParameter("Address", "dad");
        urlBuilder.addQueryParameter("LocationProvider", "dadi");
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);

        urlBuilder.addQueryParameter("UpdatedDateTime", dateToStr);
        urlBuilder.addQueryParameter("AppStatus", "1");
         urlBuilder.addQueryParameter("MobileDeviceID", "866c88530edb48c0");
        //urlBuilder.addQueryParameter("MobileDeviceID", "e372ca4f484f14c3");
       // urlBuilder.addQueryParameter("MobileDeviceID", "50209ef830f142a6");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .addHeader("appversion","1.4")
                .url(url)
                .build();
        System.out.println("dadiiiiiiii " + urlBuilder.toString());
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //Log.d("result", e.getMessage().toString());
                e.printStackTrace();
                screenLock.release();
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //Log.d("result", response.toString());
                    throw new IOException("Unexpected code " + response);
                } else {
                    //Log.d("result", response.toString());
                    String responseBody = response.body().string();

                    JSONObject obj;
                    try {
                        obj = new JSONObject(responseBody);
                        System.out.println("srinivasu srinivasu srinivasu" + obj.toString());
                        screenLock.release();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        screenLock.release();
                    }
                }
            }
        });

    }

    public void calllatlong(){

        GPSTracker gpsTracker = new GPSTracker(getBaseContext());
        System.out.println("dadi tracking "+gpsTracker.getLongitude()+" longi "+gpsTracker.getLatitude());

    }
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        calllatlong();
                        GPSTracker gpsTracker = new GPSTracker(getBaseContext());
                        try {
                            getURLRegister(gpsTracker.getLatitude(),gpsTracker.getLongitude());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
