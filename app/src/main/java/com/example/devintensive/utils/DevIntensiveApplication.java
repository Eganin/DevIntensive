package com.example.devintensive.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DevIntensiveApplication extends Application {

    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                DevIntensiveApplication.this);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        DevIntensiveApplication.sharedPreferences = sharedPreferences;
    }
}
