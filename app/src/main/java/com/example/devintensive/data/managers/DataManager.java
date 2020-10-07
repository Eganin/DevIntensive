package com.example.devintensive.data.managers;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferenceManager preferenceManager;

    public DataManager() {
        this.preferenceManager = new PreferenceManager();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public void setPreferenceManager(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }
}
