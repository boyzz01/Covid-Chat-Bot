package com.example.covidchatbot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorldCaseResponse {

    @SerializedName("TotalConfirmed")
    @Expose
    private Long totalConfirmed;
    @SerializedName("TotalDeaths")
    @Expose
    private Long totalDeaths;
    @SerializedName("TotalRecovered")
    @Expose
    private Long totalRecovered;

    public Long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

}
