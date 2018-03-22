package fr.isen.traintrain.traintrainapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Parser.StationParser;
import fr.isen.traintrain.traintrainapp.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fmunini on 23/02/2018.
 */

public class StationAsyncTask extends AsyncTask<String, String, ArrayList<Station>> {

    protected String url = null;
    protected OkHttpClient client;
    public AsyncResponse delegate = null;
    protected ProgressDialog progressDialog;
    protected Activity currentActivity;
    protected Class nextView;
    protected String json = null;

    public StationAsyncTask(ProgressDialog progressDialog, Activity currentActivity) {
        this.currentActivity = currentActivity;
        this.nextView = nextView;
        this.progressDialog = progressDialog;

        try {
            InputStream is = currentActivity.getAssets().open("stations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Station> doInBackground(String... strings) {
        ArrayList<Station> stations = null;

        try {

            publishProgress("chargement");

            System.out.println("client");

            //System.out.println(TAG+response.body().string());
            try {

                publishProgress("parsing");


                StationParser stationParser = new StationParser(json);
                stations = stationParser.getResult();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("asyncTask", "url pas bon");
        }
        return stations;
    }

    @Override
    protected void onPostExecute(ArrayList<Station> stations) {
        // System.out.println(ip.getCity());
        progressDialog.dismiss();
        delegate.processFinish(stations);
    }
    @Override
    protected void onProgressUpdate(String... values) {
        progressDialog.setMessage(values[0]);
    }

    {
    }
}
