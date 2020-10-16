package com.example.covidchatbot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CountryModel extends RealmObject {


    @PrimaryKey
    private Integer id;
    private String country;
    private String slug;
    private String iSO2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getiSO2() {
        return iSO2;
    }

    public void setiSO2(String iSO2) {
        this.iSO2 = iSO2;
    }
}