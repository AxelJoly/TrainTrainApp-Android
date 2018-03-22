package fr.isen.traintrain.traintrainapp.Entity;

import java.util.ArrayList;

/**
 * Created by maxim on 21/03/2018.
 */

public interface TripAsyncResponse {
    void processFinish(ArrayList<Journey2> output);
}
