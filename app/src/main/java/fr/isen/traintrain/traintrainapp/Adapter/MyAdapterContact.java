package fr.isen.traintrain.traintrainapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.Entity.ClickListener;
import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.Entity.Journey;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.Trip;
import fr.isen.traintrain.traintrainapp.JourneyActivity;
import fr.isen.traintrain.traintrainapp.R;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;
import fr.isen.traintrain.traintrainapp.TravelActivity;

/**
 * Created by maxim on 13/03/2018.
 */

public class MyAdapterContact extends RecyclerView.Adapter<MyAdapterContact.MyViewHolder> {

    private Context mContext;
    protected ArrayList<Contact> contacts = new ArrayList<Contact>();
    private final ClickListener listener;
    protected Activity currentActivity;
    protected Class nextView;

    public MyAdapterContact(Context context, ArrayList<Contact> contacts, Activity currentActivity, Class<TravelActivity> nextView,ClickListener listener) {
        mContext = context;
        this.contacts = contacts;
        this.listener = listener;
        this.currentActivity = currentActivity;
        this.nextView = nextView ;

    }
    @Override
    public MyAdapterContact.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view,listener,this.contacts,this.currentActivity,this.nextView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyAdapterContact.MyViewHolder holder, int position) {
        // holder.bindUser(users.get(position));

        holder.mNameTextView.setText(contacts.get(position).getNameContact());
        holder.mPhoneNumberTextView.setText(contacts.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+contacts.size());
        return contacts.size();
    }

 /*   public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameTextView;
        public TextView mPhoneNumberTextView;

        public ViewHolder(View v) {
            super(v);
            mNameTextView = (TextView)v.findViewById(R.id.nameContact);
            mPhoneNumberTextView = (TextView)v.findViewById(R.id.phoneNumber);


        }

    }*/


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView rubbish;

        private WeakReference<ClickListener> listenerRef;
        protected ArrayList<Contact> contacts = new ArrayList<Contact>();
        protected Activity currentActivity;
        protected Class nextView;




        public TextView mNameTextView;
        public TextView mPhoneNumberTextView;



      /*  public MyViewHolder(View v) {
            super(v);
            mGareDepartTextView = (TextView)v.findViewById(R.id.gareDepart);
            mGareArriveeTextView = (TextView)v.findViewById(R.id.gareArrivee);
            button = v.findViewById(R.id.launch);


        }*/

        public MyViewHolder(final View itemView, ClickListener listener, ArrayList<Contact> contacts, Activity currentActivity, Class<JourneyActivity> nextView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.nameContact);
            mPhoneNumberTextView = (TextView)itemView.findViewById(R.id.phoneNumber);

            this.contacts = contacts;
            listenerRef = new WeakReference<>(listener);

            this.rubbish = (ImageView) itemView.findViewById(R.id.rubbish);
            this.rubbish.setOnClickListener(this);
            this.currentActivity = currentActivity;
            this.nextView =nextView;


        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == rubbish.getId()) {

                //  Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Log.d("adapter",this.contacts.get(getAdapterPosition()).getNameContact());

                   FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(currentActivity);

                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                String selection = sqliteSave.FeedEntry.COLUMN_NAME_CONTACT + " LIKE ?";
// Specify arguments in placeholder order.
                String[] selectionArgs = { this.contacts.get(getAdapterPosition()).getNameContact() };
// Issue SQL statement.
                int deletedRows = db.delete(sqliteSave.FeedEntry.TABLE_NAME_CONTACT, selection, selectionArgs);

                Intent intent = new Intent(this.currentActivity,this.nextView);
                this.currentActivity.startActivity(intent);




            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }


        //onLongClickListener for view
        @Override
        public boolean onLongClick(View v) {


            return true;
        }
    }







}
