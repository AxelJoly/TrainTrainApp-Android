package fr.isen.traintrain.traintrainapp.Entity;

/**
 * Created by maxim on 13/03/2018.
 */

public class Trip {

    protected String id_depart;
    protected String id_arrivee;
    protected String name_depart;
    protected String name_arrivee;
    protected String longitude_depart;
    protected String longitude_arrivee;
    protected String latitude_depart;
    protected String latitude_arrivee;


    public Trip(String id_depart, String id_arrivee, String name_depart, String name_arrivee, String longitude_depart, String longitude_arrivee, String latitude_depart, String latitude_arrivee) {
        this.id_depart = id_depart;
        this.id_arrivee = id_arrivee;
        this.name_depart = name_depart;
        this.name_arrivee = name_arrivee;
        this.longitude_depart = longitude_depart;
        this.longitude_arrivee = longitude_arrivee;
        this.latitude_depart = latitude_depart;
        this.latitude_arrivee = latitude_arrivee;
    }

    public String getId_depart() {
        return id_depart;
    }

    public void setId_depart(String id_depart) {
        this.id_depart = id_depart;
    }

    public String getId_arrivee() {
        return id_arrivee;
    }

    public void setId_arrivee(String id_arrivee) {
        this.id_arrivee = id_arrivee;
    }

    public String getName_depart() {
        return name_depart;
    }

    public void setName_depart(String name_depart) {
        this.name_depart = name_depart;
    }

    public String getName_arrivee() {
        return name_arrivee;
    }

    public void setName_arrivee(String name_arrivee) {
        this.name_arrivee = name_arrivee;
    }

    public String getLongitude_depart() {
        return longitude_depart;
    }

    public void setLongitude_depart(String longitude_depart) {
        this.longitude_depart = longitude_depart;
    }

    public String getLongitude_arrivee() {
        return longitude_arrivee;
    }

    public void setLongitude_arrivee(String longitude_arrivee) {
        this.longitude_arrivee = longitude_arrivee;
    }

    public String getLatitude_depart() {
        return latitude_depart;
    }

    public void setLatitude_depart(String latitude_depart) {
        this.latitude_depart = latitude_depart;
    }

    public String getLatitude_arrivee() {
        return latitude_arrivee;
    }

    public void setLatitude_arrivee(String latitude_arrivee) {
        this.latitude_arrivee = latitude_arrivee;
    }
}
