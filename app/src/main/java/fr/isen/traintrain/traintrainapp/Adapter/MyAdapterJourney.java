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
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.DetailsActivity;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
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
        MyAdapterJourney.ViewHolder viewHolder = new MyAdapterJourney.ViewHolder(view, context, journeys);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mDate;
        public TextView mDurat;
        public TextView mDepartTime;
        public TextView mDepartPlace;
        public TextView mArrivalTime;
        public TextView mArrivalPlace;
        public TextView mChange;
        ArrayList<Journey2> arrayList = new ArrayList<Journey2>();
        Context ctx;

        public ViewHolder(View v, Context ctx, ArrayList<Journey2> arrayList) {
            super(v);
            this.arrayList = arrayList;
            this.ctx = ctx;
            v.setOnClickListener(this);
            mDate = (TextView)v.findViewById(R.id.date);
            mDurat = (TextView)v.findViewById(R.id.duration);
            mDepartPlace = (TextView)v.findViewById(R.id.departPlace);
            mDepartTime = (TextView)v.findViewById(R.id.waitTitle);
            mArrivalPlace = (TextView)v.findViewById(R.id.arrivalPlace);
            mArrivalTime = (TextView)v.findViewById(R.id.arrivalTime);
            mChange = (TextView)v.findViewById(R.id.line3);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Journey2 details = this.arrayList.get(position);
            Log.d("Mon objet", details.getArrivalFormat().toString());
            Intent intent = new Intent(this.ctx, DetailsActivity.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("details",details);
            this.ctx.startActivity(intent);
        }

    }

}
