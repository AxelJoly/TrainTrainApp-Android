package fr.isen.traintrain.traintrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterJourney;
import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;

public class JourneyActivity extends AppCompatActivity {

    private RecyclerView journeyView;
    private MyAdapterJourney mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        String journeysString = getIntent().getStringExtra("journeys");
        JSONArray journeysJson = toJson(journeysString);
        Log.i("Contenu JourneyString", journeysString);
        Log.i("Contenu JourneyJson", journeysJson.toString());



    }

    public JSONArray toJson(String dataString){
        JSONObject dataJSON = null;
        JSONArray journeyArray = null;
        List<Journey2> journeys = new ArrayList<>();
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
                        //Log.i("contenu section", section.toString());
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
                    add.setDepartureFormat(add.departure);
                    add.setArrivalFormat(add.arrival);
                    journeys.add(add);
                    Log.i("index journey", String.valueOf(i));
                    Log.i("journey contenu : ", journeys.get(i).getDepartureFormat());
                    Log.i("journey contenu : ", journeys.get(i).getArrivalFormat());
                    Log.i("journey contenu : ", journeys.get(i).getDuration());
                    Log.i("journey contenu : ", journeys.get(i).getPlaceFrom());
                    Log.i("journey contenu : ", journeys.get(i).getPlaceTo());

                }

                //MyAdapterJourney.notifyDataSetChanged();
                journeyView = (RecyclerView)findViewById(R.id.journeyView);
                mAdapter = new MyAdapterJourney(this, journeys);
                journeyView.setAdapter(mAdapter);
                journeyView.setLayoutManager(new LinearLayoutManager(this));

                return (journeyArray);
                //System.out.print(journeysJson.toString());
            }
            catch (Throwable t) {
                Log.e("JSON", "Malformed JSON: \"" + dataString + "\"");
                dataJSON = null;
            }

        }
        return journeyArray;
    }

}
