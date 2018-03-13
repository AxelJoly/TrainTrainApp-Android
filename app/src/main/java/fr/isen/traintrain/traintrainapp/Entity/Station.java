package fr.isen.traintrain.traintrainapp.Entity;
/**
 * Created by fmunini on 23/02/2018.
 */

public class Station {

    protected String stop_id;
    protected String name;
    protected String stop_lat;
    protected String stop_lon;
    protected String stop_desc;
    protected String zone_id;
    protected String stop_url;
    protected String location_type;
    protected String parent_station;

    public Station(String stop_id, String name, String stop_lat, String stop_lon, String stop_desc, String zone_id, String stop_url, String location_type, String parent_station) {
        this.stop_id = stop_id;
        this.name = name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_desc = stop_desc;
        this.zone_id = zone_id;
        this.stop_url = stop_url;
        this.location_type = location_type;
        this.parent_station = parent_station;
    }

    public String getParent_station() {
        return parent_station;
    }

    public void setParent_station(String parent_station) {
        this.parent_station = parent_station;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public String getStop_url() {
        return stop_url;
    }

    public void setStop_url(String stop_url) {
        this.stop_url = stop_url;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public String getStop_desc() {
        return stop_desc;
    }

    public void setStop_desc(String stop_desc) {
        this.stop_desc = stop_desc;
    }

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

    public String getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(String stop_lon) {
        this.stop_lon = stop_lon;
    }
}
