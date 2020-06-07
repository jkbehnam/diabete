package com.example.diabetes.name.base;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;


import com.example.diabetes.name.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class aaplication extends Application {

    public static aaplication application;

    @Override
    public void onCreate() {

        application=this;

        super.onCreate();
    }


}
