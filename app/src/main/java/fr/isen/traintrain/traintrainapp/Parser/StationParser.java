package fr.isen.traintrain.traintrainapp.Parser;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.Entity.Result;
import fr.isen.traintrain.traintrainapp.Entity.Station;

/**
 * Created by fmunini on 23/02/2018.
 */

public class StationParser {




        protected ArrayList<Station> stations = new ArrayList<Station>();
        protected ArrayList<Station> result;
        protected ListIterator<Station> itr;
        protected JSONObject results2;

        protected JSONArray results = null;




    public  StationParser (String response) {

        this.result = new ArrayList<Station>();

        Log.d("parser", response);

        JSONObject json = null;
        try {
            json = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            results = json.getJSONArray("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* try {
            this.results2 = results.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Gson gson = new GsonBuilder().create();
        Log.d("parser", this.results2.toString());
        // this.result = gson.fromJson(response, Result.class);
        // System.out.println("looooooooooooooooooooooooooooooooooooooooooooooool"+this.user.getCell());

      /*  System.out.println("looooooooooooooooooooooooooooooool2 " + this.result.getResults().size());
        this.itr = this.result.getResults().listIterator();
        while (itr.hasNext()){
            System.out.println("looooooooooooooooooooooooooooooool2 " + itr.next().getName());
        }
        System.out.println("looooooooooooooooooooooooooooooool2 " + this.result.getResults().size());

*/
    }

}
