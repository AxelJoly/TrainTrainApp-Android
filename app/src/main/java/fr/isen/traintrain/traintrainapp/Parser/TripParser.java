package fr.isen.traintrain.traintrainapp.Parser;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterJourney;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.R;

/**
 * Created by maxim on 21/03/2018.
 */

public class TripParser {

    protected ArrayList<Journey2> journeys = new ArrayList<>();


    public ArrayList<Journey2> getJourneys() {
        return journeys;
    }

    public void setJourneys(ArrayList<Journey2> journeys) {
        this.journeys = journeys;
    }

    public void getData(String dataString){
        JSONObject dataJSON = null;
        JSONArray journeyArray = null;

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
                    Log.d("trip parser",sectionsArray.toString());
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
                        add.setChange("Trajet direct");
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

                }





            }
            catch (Throwable t) {
                Log.e("JSON", "Malformed JSON: \"" + dataString + "\"");
                dataJSON = null;
            }

        }

    }
}
