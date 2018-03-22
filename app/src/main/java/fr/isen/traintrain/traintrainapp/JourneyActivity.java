package fr.isen.traintrain.traintrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.isen.traintrain.traintrainapp.Adapter.GeolocAdapter;
import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterJourney;
import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;

public class JourneyActivity extends AppCompatActivity {

    //private RecyclerView journeyView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        String journeysString = getIntent().getStringExtra("journeys");
        JSONArray journeysJson = getData(journeysString);
        Log.i("Contenu JourneyString", journeysString);
        Log.i("Contenu JourneyJson", journeysJson.toString());



    }

    public JSONArray getData(String dataString){
        JSONObject dataJSON = null;
        JSONArray journeyArray = null;
        ArrayList<Journey2> journeys = new ArrayList<>();
        if(dataString!= null)
        {
            try {

                journeyArray = new JSONObject(dataString).getJSONArray("journeys");

                for (int i = 0 ; i < journeyArray.length() ; i++)
                {
                    JSONObject obj=new JSONObject(journeyArray.get(i).toString());

                    Journey2 add = new Journey2();
                    add.departure = obj.getString("departure_date_time");
                    add.arrival = obj.getString("arrival_date_time");
                    add.getDuration();
                    int counter = 0;
                    //Log.i("departure contenu ", add.timeDisplayDeparture(add.getDeparture()));
                    //Log.i("arrival contenu ", add.timeDisplayArrival(add.getArrival()));
                    //Log.i("duration contenu ", add.getDuration());
                    JSONArray sectionsArray = obj.getJSONArray("sections");
                    //Log.i("contenu sectionsArray", sectionsArray.toString());
                    for (int j = 0 ; j < sectionsArray.length() ; j++)
                    {
                        //Log.i("For loop PlaceFrom", String.valueOf(j));
                        JSONObject section = sectionsArray.getJSONObject(j);
                        //Log.i("contenu section", section.toString());
                        JSONObject sectionFrom = section.getJSONObject("from");
                        //Log.i("contenu sectionFrom", sectionFrom.toString());
                        //Log.i("contenu sectionFrom get", sectionFrom.getString("embedded_type"));
                        if (sectionFrom.getString("embedded_type").equals("stop_point"))
                        {
                            JSONObject sectionPart = sectionFrom.getJSONObject("stop_point");
                            //Log.i("contenu sectionPart", sectionPart.getString("label"));
                            add.placeFrom = sectionPart.getString("label");
                            //Log.i("contenu PlaceFrom", add.placeFrom);
                            break;
                        }
                    }
                    for (int j = sectionsArray.length() - 1 ; j >= 0 ; j--)
                    {
                        //Log.i("For loop PlaceTo", String.valueOf(j));
                        JSONObject section = sectionsArray.getJSONObject(j);
                        Log.i("contenu section", section.toString());
                        JSONObject sectionTo = section.getJSONObject("to");
                        //Log.i("contenu sectionTo", sectionTo.toString());
                        //Log.i("contenu sectionTo get", sectionTo.getString("embedded_type"));
                        if (sectionTo.getString("embedded_type").equals("stop_point"))
                        {
                            JSONObject sectionPart = sectionTo.getJSONObject("stop_point");
                            //Log.i("contenu sectionPart", sectionPart.getString("label"));
                            add.placeTo = sectionPart.getString("label");
                            //Log.i("contenu PlaceTo", add.placeTo);
                            break;
                        }
                    }
                    for (int j = 0 ; j < sectionsArray.length() ; j++)
                    {
                        JSONObject section = sectionsArray.getJSONObject(j);
                        JSONObject displayInfo;
                        if( !section.isNull("display_informations"))
                        {
                            counter++;
                        }
                    }
                    if((counter-1)>0){
                        if((counter-1)>1){
                            add.setChange(String.valueOf(counter-1)+" changements");
                        } else {
                            add.setChange(String.valueOf(counter-1)+" changement");
                        }
                    } else {
                        add.setChange("trajet direct");
                    }
                    add.setDepartureFormat(add.departure);
                    add.setArrivalFormat(add.arrival);
                    add.setJourney(obj);
                    journeys.add(add);
                    Log.i("index journey", String.valueOf(i));
                    Log.i("journey contenu : ", journeys.get(i).getDepartureFormat());
                    Log.i("journey contenu : ", journeys.get(i).getArrivalFormat());
                    Log.i("journey contenu : ", journeys.get(i).getDuration());
                    Log.i("journey contenu : ", journeys.get(i).getPlaceFrom());
                    Log.i("journey contenu : ", journeys.get(i).getPlaceTo());
                    Log.i("journey contenu : ", journeys.get(i).getChange());
                    Log.i("journey contenu JSON", add.getJourney());

                }


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

                return (journeyArray);
            }
            catch (Throwable t) {
                Log.e("JSON", "Malformed JSON: \"" + dataString + "\"");
                dataJSON = null;
            }

        }
        return journeyArray;
    }

}
