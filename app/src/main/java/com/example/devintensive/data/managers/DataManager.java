package com.example.devintensive.data.managers;

import com.example.devintensive.data.network.RestService;
import com.example.devintensive.data.network.ServiceGenerator;
import com.example.devintensive.data.network.req.UserLoginReq;
import com.example.devintensive.data.network.res.UserModelRes;

import retrofit2.Call;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferenceManager preferenceManager;
    private RestService restService;

    private DataManager() {
        this.preferenceManager = new PreferenceManager();
        this.restService = ServiceGenerator.createService(RestService.class);
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

    public Call<UserModelRes> loginUser(UserLoginReq userLoginReq) {
        return restService.loginUser(userLoginReq);
    }


}
