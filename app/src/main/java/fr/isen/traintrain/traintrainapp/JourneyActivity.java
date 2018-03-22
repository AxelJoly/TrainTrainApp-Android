package fr.isen.traintrain.traintrainapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.isen.traintrain.traintrainapp.Adapter.GeolocAdapter;
import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterJourney;
import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.AsyncTask.TripAsyncTask;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.TripAsyncResponse;

public class JourneyActivity extends AppCompatActivity implements TripAsyncResponse {

    //private RecyclerView journeyView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Journey2> journeys = new ArrayList<Journey2>();
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        setTitle("Résultats trajets");
        String journeysString = getIntent().getStringExtra("journeys");
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Load");
        progressDialog.show();
        TripAsyncTask ipinf=new TripAsyncTask(progressDialog,this,journeysString);
        ipinf.delegate = this;
        ipinf.execute();






    }


    @Override
    public void processFinish(ArrayList<Journey2> output) {
        this.journeys = output;

        mRecyclerView = (RecyclerView) findViewById(R.id.journeyView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MyAdapterJourney(this.getApplicationContext(), journeys);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

    }



}
