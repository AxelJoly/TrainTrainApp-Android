package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.Trip;
import fr.isen.traintrain.traintrainapp.R;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

/**
 * Created by maxim on 13/03/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    protected ArrayList<Trip> trips = new ArrayList<Trip>();

    public MyAdapter(Context context, ArrayList<Trip> trips) {
        mContext = context;
        this.trips = trips;

    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        // holder.bindUser(users.get(position));

        holder.mGareDepartTextView.setText("De: "+trips.get(position).getName_depart());
        holder.mGareArriveeTextView.setText("A: "+trips.get(position).getName_arrivee());

    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+trips.size());
        return trips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mGareDepartTextView;
        public TextView mGareArriveeTextView;

        public ViewHolder(View v) {
            super(v);
            mGareDepartTextView = (TextView)v.findViewById(R.id.gareDepart);
            mGareArriveeTextView = (TextView)v.findViewById(R.id.gareArrivee);


        }

    }



}
