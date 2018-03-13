package fr.isen.traintrain.traintrainapp.Parser;

import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

     //   Log.d("parser", response);

        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Station.class);
        try{
            this.result = mapper.readValue(response,type );
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        /*this.itr = this.result.listIterator();
        while (itr.hasNext()){
            System.out.println("looooooooooooooooooooooooooooooool2 " + itr.next().getName());
        }*/





    }

    public ArrayList<Station> getResult() {
        return result;
    }

    public void setResult(ArrayList<Station> result) {
        this.result = result;
    }
}
