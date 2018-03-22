package fr.isen.traintrain.traintrainapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.DetailsActivity;
import fr.isen.traintrain.traintrainapp.Entity.ClickListener;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.JourneyActivity;
import fr.isen.traintrain.traintrainapp.R;

/**
 * Created by ruiz on 19/03/2018.
 */

public class MyAdapterJourney extends RecyclerView.Adapter<MyAdapterJourney.MyViewHolder> {

    private Context context;
    protected ArrayList<Journey2> journeys= new ArrayList<Journey2>();
    private final ClickListener listener;
    protected Activity currentActivity;
    protected Class nextView;

    public MyAdapterJourney(Context context, ArrayList<Journey2> journeys, ClickListener listener, Activity currentActivity, Class<DetailsActivity> nextView){
        this.context=context;
        this.journeys=journeys;
        this.listener = listener;
        this.currentActivity = currentActivity;
        this.nextView = nextView;
        ListIterator<Journey2> itr =this.journeys.listIterator();
        while (itr.hasNext()){
            System.out.println("looooooooooooooooooooooooooooooool2 " + itr.next().getDate());
        }
    }

    @Override
    public MyAdapterJourney.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journeys, parent, false);
        MyAdapterJourney.MyViewHolder viewHolder = new MyAdapterJourney.MyViewHolder(view,this.listener,this.journeys,this.currentActivity,this.nextView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapterJourney.MyViewHolder holder, int position) {

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




    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView mDate;
        public TextView mDurat;
        public TextView mDepartTime;
        public TextView mDepartPlace;
        public TextView mArrivalTime;
        public TextView mArrivalPlace;
        protected Activity currentActivity;
        protected Class nextView;
        public TextView mChange;
        protected ArrayList<Journey2> journeys = new ArrayList<Journey2>();
        public ConstraintLayout group ;
        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(View v,ClickListener listener,ArrayList<Journey2> journeys,Activity currentActivity, Class<DetailsActivity> nextView) {
            super(v);

            v.setOnClickListener(this);
            mDate = (TextView)v.findViewById(R.id.date);
            mDurat = (TextView)v.findViewById(R.id.duration);
            mDepartPlace = (TextView)v.findViewById(R.id.departPlace);
            mDepartTime = (TextView)v.findViewById(R.id.waitTitle);
            mArrivalPlace = (TextView)v.findViewById(R.id.arrivalPlace);
            mArrivalTime = (TextView)v.findViewById(R.id.arrivalTime);
            mChange = (TextView)v.findViewById(R.id.change);
            this.group = v.findViewById(R.id.journeyLayout);
            this.group.setOnClickListener(this);
            listenerRef = new WeakReference<>(listener);
            this.journeys =journeys;
            this.currentActivity = currentActivity;
            this.nextView = nextView ;


        }


        @Override
        public void onClick(View view) {

            Log.d("journey adapter","lolololol"+this.journeys.get(getAdapterPosition()).getPlaceFrom());

            Intent intent =new Intent(currentActivity,nextView);
            intent.putExtra("details",  this.journeys.get(getAdapterPosition()));

            currentActivity.startActivity(intent);



        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

}
