package fr.isen.traintrain.traintrainapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.R;

/**
 * Created by maxim on 13/03/2018.
 */

public class MyAdapterContact extends RecyclerView.Adapter<MyAdapterContact.ViewHolder> {

    private Context mContext;
    protected ArrayList<Contact> contacts = new ArrayList<Contact>();

    public MyAdapterContact(Context context, ArrayList<Contact> contacts) {
        mContext = context;
        this.contacts = contacts;

    }
    @Override
    public MyAdapterContact.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyAdapterContact.ViewHolder holder, int position) {
        // holder.bindUser(users.get(position));

        holder.mNameTextView.setText(contacts.get(position).getNameContact());
        holder.mPhoneNumberTextView.setText(contacts.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+contacts.size());
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameTextView;
        public TextView mPhoneNumberTextView;

        public ViewHolder(View v) {
            super(v);
            mNameTextView = (TextView)v.findViewById(R.id.nameContact);
            mPhoneNumberTextView = (TextView)v.findViewById(R.id.phoneNumber);


        }

    }



}
