package com.example.covidchatbot.service;


import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UtilsApi {

    public static final String BASE_URL_API = "https://api.covid19api.com/";
    public static Context context;

    // Declare Interface BaseApiService
    public static BaseApiService getAPIService(Context context){
        UtilsApi.context = context;
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
