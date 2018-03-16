package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.Geoloc;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.R;
import fr.isen.traintrain.traintrainapp.TravelActivity;

/**
 * Created by axel on 16/03/2018.
 */

public class GeolocAdapter  extends RecyclerView.Adapter<GeolocAdapter.ViewHolder>  {
    protected ArrayList<Geoloc> stations = new ArrayList<Geoloc>();
    Context ctx;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView distance;
        ArrayList<Geoloc> arrayList = new ArrayList<Geoloc>();
        Context ctx;


        public ViewHolder(View v, Context ctx, ArrayList<Geoloc> arrayList) {
            super(v);
            this.arrayList = arrayList;
            this.ctx = ctx;
            v.setOnClickListener(this);
            name = (TextView) v.findViewById(R.id.name);
            distance = (TextView) v.findViewById(R.id.distance);

        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String departure = this.arrayList.get(position).toJson();
            Log.d("Mon objet", departure);
            Intent intent = new Intent(this.ctx, TravelActivity.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("departure",departure);
            this.ctx.startActivity(intent);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GeolocAdapter(ArrayList<Geoloc> stations, Context ctx) {
        this.stations = stations;
        this.ctx = ctx;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public GeolocAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.closest_stations, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, ctx, stations);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Geoloc station = stations.get(position);
        holder.name.setText(station.getStation().getName());
        holder.distance.setText(station.getDistance());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stations.size();
    }


}
