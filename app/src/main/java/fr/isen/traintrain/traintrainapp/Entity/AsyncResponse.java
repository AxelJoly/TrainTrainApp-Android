package fr.isen.traintrain.traintrainapp.Entity;

import java.util.ArrayList;

/**
 * Created by maxim on 13/03/2018.
 */

public interface AsyncResponse {
    void processFinish(ArrayList<Station> output);
}
