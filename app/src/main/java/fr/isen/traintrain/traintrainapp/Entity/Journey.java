package fr.isen.traintrain.traintrainapp.Entity;

/**
 * Created by axel on 13/03/2018.
 */

public class Journey {

    private Integer id;

    private Station departure;

    private Station arrival;

    public Journey(Integer id, Station departure, Station arrival) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Station getDeparture() {
        return departure;
    }

    public void setDeparture(Station departure) {
        this.departure = departure;
    }

    public Station getArrival() {
        return arrival;
    }

    public void setArrival(Station arrival) {
        this.arrival = arrival;
    }
}
