package fr.isen.traintrain.traintrainapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.AsyncTask.StationAsyncTask;
import fr.isen.traintrain.traintrainapp.AsyncTask.TripAsyncTask;
import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.Entity.Geoloc;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.Journey;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;


import static java.security.AccessController.getContext;

public class TravelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse{

    protected ProgressDialog progressDialog;
    protected ArrayList<Station> stations = new ArrayList<Station>();
    protected ListIterator<Station> itr;
    protected String [] stationsName;
    protected ArrayList<String> test = new ArrayList<String>();
    protected TextView gareDepart;
    protected Station gareDepartChoisi;
    protected TextView gareArrivee;
    protected Station gareArriveeChoisi;
    public Button geoloc;
    public Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        this.stationsName = new String[]{};
        this.geoloc = findViewById(R.id.geoloc);
        this.searchButton = findViewById(R.id.search);
        this.gareDepart=(TextView)findViewById(R.id.gare_depart);
        this.gareArrivee=(TextView)findViewById(R.id.gare_arrivee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        String departure = intent.getStringExtra("departure");

        if(departure != null){
            Geoloc geolocBind = Geoloc.fromJson(departure);
            Log.d("hello", geolocBind.getStation().getName());
            this.gareDepartChoisi = geolocBind.getStation();
            Log.d("hello", "Ca passe");
            this.gareDepart.setText(this.gareDepartChoisi.getName());
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Load");
        progressDialog.show();
        StationAsyncTask ipinf=new StationAsyncTask(progressDialog,this);
        ipinf.delegate = this;
        ipinf.execute();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        geoloc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toGeoloc(v);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { search(v);}
        });


    }

    public void toGeoloc(View view) {




        Intent intent = new Intent(this, GeolocActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trajet) {
            // Handle the camera action
            Log.d("main activity","trajet");
        } else if (id == R.id.nav_favoris) {

            Log.d("main activity","favoris");
            Intent intent =new Intent(this,FavorisActivity.class);

            this.startActivity(intent);

        }

        else if(id == R.id.nav_recherche_contact){
            Intent intent =new Intent(this,ShowContactActivity.class);

            this.startActivity(intent);
        }
        else if(id == R.id.nav_ajout_contact){
            Intent intent =new Intent(this,AddContactActivity.class);

            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void processFinish(ArrayList<Station> output) {

        Log.d("activity","lol");

        this.stations = output;


        this.itr = this.stations.listIterator();
        while (itr.hasNext()){
            //System.out.println("activity " + itr.next().getName());
            this.test.add(itr.next().getName());
        }




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, this.test);
        AutoCompleteTextView textView =findViewById(R.id.gare_depart);

        textView.setAdapter(adapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, this.test);
        AutoCompleteTextView textView2 =findViewById(R.id.gare_arrivee);

        textView2.setAdapter(adapter2);

    }

    public void search(View view){
        Log.d("lol","lol");

        this.itr = this.stations.listIterator();

        Station temp;

        while (itr.hasNext()){
            //System.out.println("activity " + itr.next().getName());
            temp = itr.next();

            if(this.gareDepart.getText().toString().equals(temp.getName())){
                Log.d("gare deépart",this.gareDepart.getText().toString());
                Log.d("itr = ",temp.getName());
                this.gareDepartChoisi = temp;
                Log.d("Gare départ selectioné","name = "+this.gareDepartChoisi.getName());
                Log.d("Gare départ selectioné ","longitude = "+this.gareDepartChoisi.getStop_lon());
                Log.d("Gare départ selectioné ","latitude = "+this.gareDepartChoisi.getStop_lat());
            }

            if(this.gareArrivee.getText().toString().equals(temp.getName())){
                
                this.gareArriveeChoisi = temp;
                Log.d("Gare arrivee selectioné","name = "+this.gareArriveeChoisi.getName());
                Log.d("Gare arrivee selectioné","longitude = "+this.gareArriveeChoisi.getStop_lon());
                Log.d("Gare arrivee selectioné","latitude = "+this.gareArriveeChoisi.getStop_lat());
            }


        }
        if((this.gareDepartChoisi != null) &&(this.gareArriveeChoisi != null)) {
            Journey journey = new Journey(1, this.gareDepartChoisi, this.gareArriveeChoisi);

            Intent intent = new Intent(this, JourneyActivity.class);
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
            startActivity(intent);


        }
    }


    public void favoris( View view){


        this.gareDepart=(TextView)findViewById(R.id.gare_depart);
        this.gareArrivee=(TextView)findViewById(R.id.gare_arrivee);
        this.itr = this.stations.listIterator();

        Station temp;

        while (itr.hasNext()){
            //System.out.println("activity " + itr.next().getName());
            temp = itr.next();

            if(this.gareDepart.getText().toString().equals(temp.getName())){
                Log.d("gare deépart",this.gareDepart.getText().toString());
                Log.d("itr = ",temp.getName());
                this.gareDepartChoisi = temp;
                Log.d("Gare départ selectioné","name = "+this.gareDepartChoisi.getName());
                Log.d("Gare départ selectioné ","longitude = "+this.gareDepartChoisi.getStop_lon());
                Log.d("Gare départ selectioné ","latitude = "+this.gareDepartChoisi.getStop_lat());
            }

            if(this.gareArrivee.getText().toString().equals(temp.getName())){

                this.gareArriveeChoisi = temp;
                Log.d("Gare arrivee selectioné","name = "+this.gareArriveeChoisi.getName());
                Log.d("Gare arrivee selectioné","longitude = "+this.gareArriveeChoisi.getStop_lon());
                Log.d("Gare arrivee selectioné","latitude = "+this.gareArriveeChoisi.getStop_lat());
            }


        }
        if((this.gareDepartChoisi != null) &&(this.gareArriveeChoisi != null)) {
            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_DEPART, this.gareDepartChoisi.getName());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE, this.gareArriveeChoisi.getName());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART, this.gareDepartChoisi.getStop_id());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE, this.gareArriveeChoisi.getStop_id());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE, this.gareArriveeChoisi.getStop_lat());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART, this.gareDepartChoisi.getStop_lat());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE, this.gareArriveeChoisi.getStop_lon());
            values.put(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART, this.gareDepartChoisi.getStop_lon());

// Insert the new row, returning the primary key value of the new row


            long newRowId = db.insert(sqliteSave.FeedEntry.TABLE_NAME_JOURNEY, null, values);
            AlertDialog alertDialog = new AlertDialog.Builder(TravelActivity.this).create();
            alertDialog.setTitle("Ajouté aux favoris");
            alertDialog.setMessage("Votre trajet favoris à bien été ajouté");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent intent = new Intent(TravelActivity.this, FavorisActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }



    }


}
