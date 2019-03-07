package com.gitam.backgroundservice.security;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gitam.backgroundservice.R;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Scanning extends Activity {
    ImageView done_img;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
        done_img = findViewById(R.id.done_img);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                done_img.setVisibility(View.VISIBLE);
            }
        }, 3000);
        clearCache(Scanning.this,5);

    }

    static int clearCacheFolder(final File dir, final int numDays) {

        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    System.out.println("srinivasu "+child.getAbsolutePath());
                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        System.out.println("srinivasu "+child.getAbsolutePath());
                        deletedFiles += clearCacheFolder(child, numDays);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }

                }
            }
            catch(Exception e) {
                Log.e("ATTENTION!", String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }

    public static void clearCache(final Context context, final int numDays) {
        Log.i("ADVL", String.format("Starting cache prune, deleting files older than %d days", numDays));
        int numDeletedFiles = clearCacheFolder(context.getCacheDir(), numDays);
        Log.i("ADVL", String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
    }

 }
