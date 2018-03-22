package fr.isen.traintrain.traintrainapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.TripAsyncResponse;
import fr.isen.traintrain.traintrainapp.JourneyActivity;
import fr.isen.traintrain.traintrainapp.Parser.StationParser;
import fr.isen.traintrain.traintrainapp.Parser.TripParser;
import okhttp3.OkHttpClient;

/**
 * Created by maxim on 21/03/2018.
 */

public class TripAsyncTask  extends AsyncTask<String,String,ArrayList<Journey2>> {

    protected String url = null;

    public TripAsyncResponse delegate = null;


    protected ProgressDialog progressDialog;
    protected Activity currentActivity;
    protected Class nextView;
    protected String json = null;

 //   protected ArrayList<Journey2> journeys = new ArrayList<>();



    public TripAsyncTask(ProgressDialog progressDialog, Activity currentActivity,String journeyData) {
        this.currentActivity = currentActivity;
        this.nextView = nextView;
        this.json = journeyData;



        this.progressDialog=progressDialog;







    }

    @Override
    protected ArrayList<Journey2> doInBackground(String... strings) {
        ArrayList<Journey2> journeys = null;




        try {

            publishProgress("chargement");

            System.out.println("client");




            //System.out.println(TAG+response.body().string());
            try {


                publishProgress("parsing");


                TripParser trip = new TripParser();
                trip.getData(this.json);
                journeys = trip.getJourneys();
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
            Log.d("asyncTask","url pas bon");
        }


        return journeys;
    }

    @Override
    protected void onPostExecute(ArrayList<Journey2> journeys) {

        ListIterator<Journey2> itr = journeys.listIterator();
        while (itr.hasNext()){
            System.out.println("looooooooooooooooooooooooooooooool2 " + itr.next().getPlaceFrom());
        }

        progressDialog.dismiss();
        delegate.processFinish(journeys);






    }

    @Override
    protected void onProgressUpdate(String... values) {
        progressDialog.setMessage(values[0]);
    }

    {
    }
}
