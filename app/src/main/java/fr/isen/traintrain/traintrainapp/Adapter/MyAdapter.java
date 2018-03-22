package fr.isen.traintrain.traintrainapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.Entity.ClickListener;
import fr.isen.traintrain.traintrainapp.Entity.Journey;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    protected ArrayList<Trip> trips = new ArrayList<Trip>();
    protected int position;
    private final ClickListener listener;
    protected Activity currentActivity;
    protected Class nextView;
    protected Class mainView;


    public MyAdapter(Context context, ArrayList<Trip> trips, ClickListener listener, Activity currentActivity, Class<JourneyActivity> nextView,Class<TravelActivity> mainView) {
        mContext = context;
        this.trips = trips;
        this.listener =listener;
        this.currentActivity = currentActivity;
        this.nextView = nextView;
        this.mainView = mainView;

    }
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view,listener,this.trips,this.currentActivity,this.nextView,this.mainView);
        return viewHolder;
    }

   /* @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_layout), parent, false), listener);
    }*/


    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        // holder.bindUser(users.get(position));
        this.position = position;

        holder.mGareDepartTextView.setText("De: "+trips.get(position).getName_depart());
        holder.mGareArriveeTextView.setText("A: "+trips.get(position).getName_arrivee());
       /* holder.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("adapter","launch"+trips.get(position).getName_depart());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        System.out.println("looooooooooooooooooooooooooooooool"+trips.size());
        return trips.size();
    }

   /* public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mGareDepartTextView;
        public TextView mGareArriveeTextView;
        public Button button;

        public ViewHolder(View v) {
            super(v);
            mGareDepartTextView = (TextView)v.findViewById(R.id.gareDepart);
            mGareArriveeTextView = (TextView)v.findViewById(R.id.gareArrivee);
             button = v.findViewById(R.id.launch);


        }

    }*/


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView iconImageView;
        private TextView iconTextView;
        private WeakReference<ClickListener> listenerRef;
        protected ArrayList<Trip> trips = new ArrayList<Trip>();
        protected Activity currentActivity;
        protected Class nextView;
        protected Class mainView;



        public TextView mGareDepartTextView;
        public TextView mGareArriveeTextView;
        public Button button;
        public Button deleteButton;
        protected Station gareDepartChoisi;
        protected Station gareArriveeChoisi;

      /*  public MyViewHolder(View v) {
            super(v);
            mGareDepartTextView = (TextView)v.findViewById(R.id.gareDepart);
            mGareArriveeTextView = (TextView)v.findViewById(R.id.gareArrivee);
            button = v.findViewById(R.id.launch);


        }*/

        public MyViewHolder(final View itemView, ClickListener listener, ArrayList<Trip> trips,Activity currentActivity, Class<JourneyActivity> nextView,Class<TravelActivity> mainView) {
            super(itemView);
            mGareDepartTextView = (TextView)itemView.findViewById(R.id.gareDepart);
            mGareArriveeTextView = (TextView)itemView.findViewById(R.id.gareArrivee);

            this.trips = trips;
            listenerRef = new WeakReference<>(listener);
            this.deleteButton = itemView.findViewById(R.id.delete);
            this.deleteButton.setOnClickListener(this);
            button = itemView.findViewById(R.id.launch);
            button.setOnClickListener(this);
            this.currentActivity = currentActivity;
            this.nextView =nextView;
            this.gareArriveeChoisi = new Station();
            this.gareDepartChoisi = new Station();
            this.mainView = mainView;
         /*   iconImageView = (ImageView) itemView.findViewById(R.id.myRecyclerImageView);
            iconTextView = (TextView) itemView.findViewById(R.id.myRecyclerTextView);

            itemView.setOnClickListener(this);
            iconTextView.setOnClickListener(this);
            iconImageView.setOnLongClickListener(this);*/
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == button.getId()) {
                this.gareDepartChoisi.setName(this.trips.get(getAdapterPosition()).getName_depart());
                this.gareDepartChoisi.setStop_id(this.trips.get(getAdapterPosition()).getId_depart());
                this.gareDepartChoisi.setStop_lat(this.trips.get(getAdapterPosition()).getLatitude_depart());
                this.gareDepartChoisi.setStop_lon(this.trips.get(getAdapterPosition()).getLongitude_depart());
                this.gareArriveeChoisi.setName(this.trips.get(getAdapterPosition()).getName_arrivee());
                this.gareArriveeChoisi.setStop_id(this.trips.get(getAdapterPosition()).getId_arrivee());
                this.gareArriveeChoisi.setStop_lon(this.trips.get(getAdapterPosition()).getLongitude_arrivee());
                this.gareArriveeChoisi.setStop_lat(this.trips.get(getAdapterPosition()).getLatitude_arrivee());
              //  Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Log.d("adapter",this.trips.get(getAdapterPosition()).getName_arrivee());
                Journey journey = new Journey(1, this.gareDepartChoisi, this.gareArriveeChoisi);

                Intent intent = new Intent(this.currentActivity,this.nextView);
                String journeyData = null;
                try {
                    journeyData = new JourneyServiceTask().execute(journey).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i("journeyData :", journeyData);
                intent.putExtra("journeys", journeyData);
                this.currentActivity.startActivity(intent);

            } else {
               // Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();

                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(currentActivity);

                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                String selection = sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART + " LIKE ?";
// Specify arguments in placeholder order.
                String[] selectionArgs = { this.trips.get(getAdapterPosition()).getId_depart() };
// Issue SQL statement.
                int deletedRows = db.delete(sqliteSave.FeedEntry.TABLE_NAME_JOURNEY, selection, selectionArgs);

                Intent intent = new Intent(this.currentActivity,this.mainView);
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
