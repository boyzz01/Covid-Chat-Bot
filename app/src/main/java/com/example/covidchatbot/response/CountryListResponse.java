package com.example.covidchatbot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CountryListResponse {

    @SerializedName("Slug")
    @Expose
    private String slug;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("ISO2")
    @Expose
    private String iSO2;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getISO2() {
        return iSO2;
    }

    public void setISO2(String iSO2) {
        this.iSO2 = iSO2;
    }

}