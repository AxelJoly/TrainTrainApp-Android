package fr.isen.traintrain.traintrainapp.Entity;

/**
 * Created by fmunini on 23/02/2018.
 */

public class Station {

    protected String stop_id;
    protected String name;
    protected String stop_lat;
    protected String stop_long;


    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStop_long() {
        return stop_long;
    }

    public void setStop_long(String stop_long) {
        this.stop_long = stop_long;
    }
}
