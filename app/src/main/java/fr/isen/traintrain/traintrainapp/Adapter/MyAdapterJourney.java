package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fr.isen.traintrain.traintrainapp.Entity.Journey2;

/**
 * Created by ruiz on 19/03/2018.
 */

public class MyAdapterJourney extends RecyclerView.Adapter<MyAdapterContact.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Journey2> data= Collections.emptyList();
    Journey2 current;

    public MyAdapterJourney(Context context, List<Journey2> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @NonNull
    @Override
    public MyAdapterContact.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterContact.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
