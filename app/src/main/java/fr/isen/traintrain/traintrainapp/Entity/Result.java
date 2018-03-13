package fr.isen.traintrain.traintrainapp.Entity;

import java.util.ArrayList;

/**
 * Created by fmunini on 23/02/2018.
 */

public class Result {


    protected ArrayList<Station> results = new ArrayList<Station>();


    public ArrayList<Station> getResults() {
        return results;
    }

    public void setResults(ArrayList<Station> results) {
        this.results = results;
    }
}