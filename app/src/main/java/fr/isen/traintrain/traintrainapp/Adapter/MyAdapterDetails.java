package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.ClickListener;
import fr.isen.traintrain.traintrainapp.Entity.Details;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.R;

/**
 * Created by ruiz on 21/03/2018.
 */

public class MyAdapterDetails extends RecyclerView.Adapter<MyAdapterDetails.ViewHolder>{

    private Context context;
    ArrayList<Details> sections= new ArrayList<Details>();
    Journey2 journey = new Journey2();

    public MyAdapterDetails(Context context, ArrayList<Details> sections, Journey2 journey){
        this.context=context;
        this.sections=sections;
        Log.i("section length", String.valueOf(sections.size()));
        Log.i("section length", String.valueOf(sections.get(1)));
        this.journey=journey;
    }

    @Override
    public MyAdapterDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail, parent, false);
        MyAdapterDetails.ViewHolder viewHolder = new MyAdapterDetails.ViewHolder(view, context, sections, journey);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapterDetails.ViewHolder holder, int position) {

        Log.d("adapter","lolool");
        int listSize = sections.size();

        if(position == 0){

            holder.mLine1.setText("Date : " + journey.getDate() + " | Durée : " + journey.getDuration());
            holder.mLine2.setText("Temps avant départ : " + sections.get(0).getTimeLeft());
            holder.mLine3.setText(sections.get(sections.size()-1).getChange());
        } else {
            holder.mImg.setVisibility(View.GONE);
            if ((position > 0) && (position < sections.size()-1)) {
                if (!sections.get(position).fromPlace.isEmpty()) {
                    Log.i("Départ depuis : ", sections.get(position).getFromPlace());
                    Log.i("Position : ", String.valueOf(position));
                    holder.mLine1.setText("Départ depuis : " + sections.get(position).getFromPlace());
                    holder.mLine2.setText("Arrivée à : " + sections.get(position).getToPlace());
                    holder.mLine3.setText("Temps du trajet : " + sections.get(position).getDuration() + "min");
                }
            } else {
                holder.itemView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+sections.size());
        return sections.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mLine1;
        public TextView mLine2;
        public TextView mLine3;
        public ImageView mImg;

        ArrayList<Details> arrayList = new ArrayList<Details>();
        Context ctx;

        public ViewHolder(View v, Context ctx, ArrayList<Details> arrayList, Journey2 journey2) {
            super(v);
            this.arrayList = arrayList;
            this.ctx = ctx;
            mLine1 = (TextView)v.findViewById(R.id.line1);
            mLine2 = (TextView)v.findViewById(R.id.line2);
            mLine3 = (TextView)v.findViewById(R.id.line3);
            mImg = (ImageView)v.findViewById((R.id.trainIcoon));


        }

    }
}
