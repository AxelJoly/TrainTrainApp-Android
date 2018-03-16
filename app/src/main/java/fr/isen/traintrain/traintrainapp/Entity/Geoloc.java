package fr.isen.traintrain.traintrainapp.Entity;

import com.google.gson.Gson;

/**
 * Created by axel on 16/03/2018.
 */

public class Geoloc {

    private Station station;

    private String distance;

    public Geoloc(Station station, String distance) {
        this.station = station;
        this.distance = distance;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Geoloc fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Geoloc.class);
    }
}
