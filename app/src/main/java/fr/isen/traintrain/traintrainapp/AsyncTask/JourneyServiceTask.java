package fr.isen.traintrain.traintrainapp.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.IOException;

import fr.isen.traintrain.traintrainapp.Entity.Journey;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.env.Environment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by axel on 13/03/2018.
 */

public class JourneyServiceTask  extends AsyncTask<Journey, Integer, String> {
    public OkHttpClient client;
    public Journey journey;
    public Environment env;
   // public AsyncResponse delegate = null;

    /*public JourneyServiceTask(AsyncResponse asyncResponse) {
        this.delegate = asyncResponse;
    }*/

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //  mProgressBar.setProgress(values[0]);
    }

    @Override
    protected String doInBackground(Journey... journey) {
        // String data = getJSON("https://randomuser.me/api/?key=4Z79-GPX2-23AF-HOWH&ref=poppy83", 2000);
        // System.out.println(data);


        this.client = new OkHttpClient();
        this.env = new Environment();
        String url = env.getSNCF_API_ROOT() + "from=" + journey[0].getDeparture().getStop_lon() + "%3B" + journey[0].getDeparture().getStop_lat() + "&to=" + journey[0].getArrival().getStop_lon() + "%3B" + journey[0].getArrival().getStop_lat() + "&count=10&";
        Log.d("Mon url", url);
        try{
            String data = run(url);
            System.out.println("mon json:" + data);

            Log.d("mon json: ", data);

        }catch(IOException e){
            Log.d("error", e.getLocalizedMessage());
        }

      return "lol";
    }



    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", this.env.getSCNF_API_TOKEN())
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }




}
