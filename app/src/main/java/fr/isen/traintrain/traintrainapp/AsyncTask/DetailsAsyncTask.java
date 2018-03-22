package fr.isen.traintrain.traintrainapp.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import fr.isen.traintrain.traintrainapp.DetailsActivity;
import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Details;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;

import static java.lang.Integer.parseInt;

/**
 * Created by ruiz on 21/03/2018.
 */

public class DetailsAsyncTask extends AsyncTask<String,String,ArrayList<Details>> {
    private ProgressDialog pDialog;
    public AsyncResponse delegate = null;
    Journey2 journey = null;
    String json = null;

    public DetailsAsyncTask(Journey2 journey, String json) {
        this.journey = journey;
        this.json = json;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //@Override
    protected void onPostExecute(ArrayList<String> result) {
        //pDialog.dismiss();
    }

    protected ArrayList<Details> doInBackground(String... strings) {

        ArrayList<Details> resultCollection = new ArrayList<Details>();
        Details resultRoot = new Details();
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(json);

        resultRoot.setTimeLeft(timeLeft(journey.getDeparture()));
        resultCollection.add(resultRoot);
        int counter = 0;
        try {
            JsonArray values = rootElement.getAsJsonObject().get("sections").getAsJsonArray();
            for (JsonElement value : values) {
                JsonObject obj = value.getAsJsonObject();
                Details resultElem = new Details();

                //if ((obj.get("from").getAsJsonObject().get("embedded_type").getAsString().equals("stop_point"))&&(obj.get("to").getAsJsonObject().get("embedded_type").getAsString().equals("stop_point"))) {
                if(obj.has("display_informations")){
                    Log.i("from", obj.get("from").getAsJsonObject().get("stop_point").getAsJsonObject().get("name").getAsString());
                    Log.i("to", obj.get("to").getAsJsonObject().get("stop_point").getAsJsonObject().get("name").getAsString());
                    resultElem.setFromPlace(obj.get("from").getAsJsonObject().get("stop_point").getAsJsonObject().get("name").getAsString());
                    resultElem.setToPlace(obj.get("to").getAsJsonObject().get("stop_point").getAsJsonObject().get("name").getAsString());
                    resultElem.setDuration(durationMinutes(obj.get("duration").getAsString()));
                    counter++;
                    System.out.println("COUNTER" + counter);
                    resultCollection.add(resultElem);
                }
            }
            if((counter-1)>0){
                if((counter-1)>1){
                    resultRoot.setChange(String.valueOf(counter-1)+" changements");
                    resultCollection.add(resultRoot);
                } else {
                    resultRoot.setChange(String.valueOf(counter-1)+" changement");
                    //resultCollection.add(resultList);
                    resultCollection.add(resultRoot);
                }
            } else {
                resultRoot.setChange("trajet direct");
                //resultCollection.add(resultList);
                resultCollection.add(resultRoot);
            }

        } catch (Throwable t){
            t.printStackTrace();
            Log.e("JsonParser","Houston, on a un problème");
        }
        // Getting JSON from URL
        /*for (int i=0; i<result.size();i++){
            System.out.println(result.get(i));
        }*/
        return resultCollection;

    }

    String durationMinutes(String string){
        Integer resultInt = parseInt(string)/60;
        String resultString = resultInt.toString();
        return resultString;
    }

    public String timeLeft(String string){

        Date currentDate = new Date();
        System.out.println(currentDate.toString());
        Integer hour;
        Integer minutes;
        Integer hourNow = parseInt(currentDate.toString().substring(11,13));
        Integer minutesNow = parseInt(currentDate.toString().substring(14,16));
        hour = parseInt(string.substring(9, 11));
        minutes = parseInt(string.substring(11, 13));

        if (hourNow > hour) {
            if (minutesNow > minutes) {

                if (hour + 23 - hourNow < 10) {

                    if (minutes + 60 - minutesNow < 10) {
                        return ("0" + (hour + 23 - hourNow) + "h et 0" + (minutes + 60 - minutesNow)+ "min");
                    } else {
                        return ("0" + (hour + 23 - hourNow) + "h et " + (minutes + 60 - minutesNow)+ "min");
                    }
                } else {
                    if (minutes + 60 - minutesNow < 10) {
                        return ((hour + 23 - hourNow) + "h et 0" + (minutes + 60 - minutesNow)+ "min");
                    } else {
                        return ((hour + 23 - hourNow) + "h et" + (minutes + 60 - minutesNow)+ "min");
                    }
                }

            } else {
                if (hour + 24 - hourNow < 10) {

                    if (minutes - minutesNow < 10) {
                        return ("0" + (hour + 24 - hourNow) + "h et 0" + (minutes - minutesNow)+ "min");
                    } else {
                        return ("0" + (hour + 24 - hourNow) + "h et " + (minutes - minutesNow)+ "min");
                    }
                } else {
                    if (minutes + 60 - minutesNow < 10) {
                        return ((hour + 24 - hourNow) + "h et 0" + (minutes - minutesNow)+ "min");
                    } else {
                        return ((hour + 24 - hourNow) + "h et " + (minutes - minutesNow)+ "min");
                    }
                }
                //return ((hourArrival+24-hourDeparture)+":"+(minutesArrival-minutesDeparture));
            }
        } else {
            if (minutesNow > minutes) {

                if (hour - 1 - hourNow < 10) {

                    if (minutes + 60 - minutesNow < 10) {
                        return ("0" + (hour - 1 - hourNow) + "h et 0" + (minutes + 60 - minutesNow)+ "min");
                    } else {
                        return ("0" + (hour - 1 - hourNow) + "h et " + (minutes + 60 - minutesNow)+ "min");
                    }
                } else {
                    if (minutes + 60 - minutesNow < 10) {
                        return ((hour - 1 - hourNow) + "h et 0" + (minutes + 60 - minutesNow)+ "min");
                    } else {
                        return ((hour - 1 - hourNow) + "h et " + (minutes + 60 - minutesNow)+ "min");
                    }
                }

            } else {
                if (hour - hourNow < 10) {

                    if (minutes - minutesNow < 10) {
                        return ("0" + (hour - hourNow) + "h et 0" + (minutes - minutesNow)+ "min");
                    } else {
                        return ("0" + (hour - hourNow) + "h et " + (minutes - minutesNow)+ "min");
                    }
                } else {
                    if (minutes + 60 - minutesNow < 10) {
                        return ((hour - hourNow) + "h et 0" + (minutes - minutesNow)+ "min");
                    } else {
                        return ((hour - hourNow) + "h et " + (minutes - minutesNow)+ "min");
                    }
                }
            }
        }
    }
}