package com.example.covidchatbot.service;

import com.example.covidchatbot.response.CountryCaseResponse;
import com.example.covidchatbot.response.CountryListResponse;
import com.example.covidchatbot.response.WorldCaseResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {


    //Country Case API
    @GET("total/country/{countryCode}")
    Call<List<CountryCaseResponse>> getCountryCase(@Path("countryCode") String countryCode);

    //World Case API
    @GET("world/total")
    Call<WorldCaseResponse> getWorldCase();

    @GET("countries")
    Call<List<CountryListResponse>> getCountryList();
}

