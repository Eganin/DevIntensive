package com.example.devintensive.data.managers;

import android.content.SharedPreferences;

import com.example.devintensive.utils.ConstantManager;
import com.example.devintensive.utils.DevIntensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {

    private SharedPreferences sharedPreferences;

    private static final String[] USER_FIELDS = new String[]{
            ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_EMAIL_KEY,
            ConstantManager.USER_VK_KEY,
            ConstantManager.USER_GIT_KEY,
            ConstantManager.USER_INFO_KEY
    };

    public PreferenceManager() {
        this.sharedPreferences = DevIntensiveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }

        editor.apply();
    }

    public List<String> loadUserProfileData() {
        List<String> userFields = new ArrayList<String>();
        userFields.add(sharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "null"));
        userFields.add(sharedPreferences.getString(ConstantManager.USER_EMAIL_KEY, "null"));
        userFields.add(sharedPreferences.getString(ConstantManager.USER_VK_KEY, "null"));
        userFields.add(sharedPreferences.getString(ConstantManager.USER_GIT_KEY, "null"));
        userFields.add(sharedPreferences.getString(ConstantManager.USER_INFO_KEY, "null"));

        return userFields;
    }
}
