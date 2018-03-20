package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.R;

/**
 * Created by ruiz on 19/03/2018.
 */

public class MyAdapterJourney extends RecyclerView.Adapter<MyAdapterJourney.ViewHolder> {

    private Context context;
    ArrayList<Journey2> journeys= new ArrayList<Journey2>();

    public MyAdapterJourney(Context context, ArrayList<Journey2> journeys){
        this.context=context;
        this.journeys=journeys;
        ListIterator<Journey2> itr =this.journeys.listIterator();
        while (itr.hasNext()){
            System.out.println("looooooooooooooooooooooooooooooool2 " + itr.next().getDate());
        }
    }

    @Override
    public MyAdapterJourney.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journeys, parent, false);
        MyAdapterJourney.ViewHolder viewHolder = new MyAdapterJourney.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapterJourney.ViewHolder holder, int position) {

        Log.d("adapter","lolool");
        holder.mDate.setText(journeys.get(position).getDate());
        holder.mDurat.setText(journeys.get(position).getDuration());
        holder.mDepartTime.setText(journeys.get(position).getDepartureTime());
        holder.mDepartPlace.setText(journeys.get(position).getPlaceFrom());
        holder.mArrivalTime.setText(journeys.get(position).getArrivalTime());
        holder.mArrivalPlace.setText(journeys.get(position).getPlaceTo());
        holder.mChange.setText(journeys.get(position).getChange());
    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+journeys.size());
        return journeys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mDate;
        public TextView mDurat;
        public TextView mDepartTime;
        public TextView mDepartPlace;
        public TextView mArrivalTime;
        public TextView mArrivalPlace;
        public TextView mChange;

        public ViewHolder(View v) {
            super(v);
            mDate = (TextView)v.findViewById(R.id.date);
            mDurat = (TextView)v.findViewById(R.id.duration);
            mDepartPlace = (TextView)v.findViewById(R.id.departPlace);
            mDepartTime = (TextView)v.findViewById(R.id.departTime);
            mArrivalPlace = (TextView)v.findViewById(R.id.arrivalPlace);
            mArrivalTime = (TextView)v.findViewById(R.id.arrivalTime);
            mChange = (TextView)v.findViewById(R.id.change);


        }

    }

}
