package fr.isen.traintrain.traintrainapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.AsyncTask.JourneyServiceTask;
import fr.isen.traintrain.traintrainapp.AsyncTask.StationAsyncTask;
import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.Journey;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

import static java.security.AccessController.getContext;

public class TravelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse {

    protected ProgressDialog progressDialog;
    protected ArrayList<Station> stations = new ArrayList<Station>();
    protected ListIterator<Station> itr;
    protected String [] stationsName;
    protected ArrayList<String> test = new ArrayList<String>();
    protected TextView gareDepart;
    protected Station gareDepartChoisi;
    protected TextView gareArrivee;
    protected Station gareArriveeChoisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        this.stationsName = new String[]{};
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.travel, menu);
        return true;
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

        Journey journey = new Journey(1, this.gareDepartChoisi, this.gareArriveeChoisi);

        new JourneyServiceTask().execute(journey);
    }

    public void favoris(View view){
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(sqliteSave.FeedEntry.COLUMN_NAME_DEPART, "lol");
        values.put(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE, "lol2");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(sqliteSave.FeedEntry.TABLE_NAME_JOURNEY, null, values);
    }
}
