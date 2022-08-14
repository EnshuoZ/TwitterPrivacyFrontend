package com.example.twitterprivacy.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationResponse {
    @SerializedName("countries")
    ArrayList<String> countries;
    @SerializedName("cities")
    ArrayList<String> cities;

    public LocationResponse(ArrayList<String> countries, ArrayList<String> cities) {
        this.countries = countries;
        this.cities = cities;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public ArrayList<String> getCities() {
        return cities;
    }
}
