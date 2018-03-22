package fr.isen.traintrain.traintrainapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterDetails;
import fr.isen.traintrain.traintrainapp.AsyncTask.DetailsAsyncTask;
import fr.isen.traintrain.traintrainapp.Entity.Details;
import fr.isen.traintrain.traintrainapp.Entity.Journey2;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected String json;
    protected Journey2 journeysString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Intent thisIntent =getIntent();
       /* json=(String)thisIntent.getExtras().get("json");
        Log.d("detail activity json = ", json);*/
         this.journeysString = (Journey2) getIntent().getSerializableExtra("details");
        Log.d("detail activity",journeysString.getJourney());
        ArrayList<Details> sectionList = new ArrayList<Details>();
        try {
            sectionList = new DetailsAsyncTask( journeysString ,journeysString.getJourney()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.detailsView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MyAdapterDetails(this.getApplicationContext(), sectionList, journeysString);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        //DetailsAsyncTask ipinf =new DetailsAsyncTask(journeysString, journeysString.getJourney());
        //ipinf.delegate = this;
        //ipinf.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*public JSONArray getData(Journey2 journeyData){
        JSONObject dataJSON = null;
        JSONArray sectionsArray = null;
        ArrayList<Journey2> journeys = new ArrayList<>();

                mRecyclerView = (RecyclerView) findViewById(R.id.journeyView);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);


                // specify an adapter (see also next example)
                mAdapter = new MyAdapterJourney(this.getApplicationContext(), journeys);
                mRecyclerView.setAdapter(mAdapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),1);
                mRecyclerView.addItemDecoration(dividerItemDecoration);


    }*/



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
        getMenuInflater().inflate(R.menu.details, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share(View v){
        Log.d("detail activity","share");
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                sqliteSave.FeedEntry.COLUMN_NAME_CONTACT,
                sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER,

        };

        Cursor cursor = db.query(
                sqliteSave.FeedEntry.TABLE_NAME_CONTACT,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null             // The sort order
        );
        /////////////////////////////////////////////////////////////////////////////////////////////
        /// Share with sms
        ///////////////////////////////////////////////////////////////////////////////////////////
        while(cursor.moveToNext()) {

            Log.d("name = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT)));
            Log.d("phone number = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER)));

            //  this.trips.add(new Trip(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE))));

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER))));
            intent.putExtra("sms_body", "Mon train part à "+this.journeysString.getDepartureTime());
            startActivity(intent);

        }
        cursor.close();

        /////////////////////////////////////////////////////////////////////////////////////////////
        /// Share with messenger
        ///////////////////////////////////////////////////////////////////////////////////////////

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        "Mon train part à "+this.journeysString.getDepartureTime());
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");
        try {
            startActivity(sendIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);



        alertDialog.setTitle("Share");
        alertDialog.setMessage("Messenger or SMS ?");
        alertDialog.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        alertDialog.setNegativeButton("Messenger", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    public  void shareSms(){
        Log.d("share sms",this.journeysString.getJourney());
    }
}
