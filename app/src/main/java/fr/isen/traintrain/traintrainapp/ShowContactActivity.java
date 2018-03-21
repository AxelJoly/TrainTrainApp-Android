package fr.isen.traintrain.traintrainapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
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

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterContact;
import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

public class ShowContactActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected ArrayList<Contact> contacts = new ArrayList<Contact>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Recherche contacts");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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




        while(cursor.moveToNext()) {

            Contact contact = new Contact();


            //Log.d("name = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT)));
            //Log.d("phone number = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER)));



            //  this.trips.add(new Trip(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE))));

            contact.setNameContact(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT)));
            contact.setPhoneNumber("Numéro de télephone: "+cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER)));
            this.contacts.add(contact);


        }
        cursor.close();


        mRecyclerView = (RecyclerView) findViewById(R.id.showContactList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MyAdapterContact(this,this.contacts);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
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
        getMenuInflater().inflate(R.menu.show_contact, menu);
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
            Intent intent =new Intent(this,TravelActivity.class);

            this.startActivity(intent);
        } else if (id == R.id.nav_favoris) {

            Log.d("main activity","favoris");
            Intent intent =new Intent(this,FavorisActivity.class);

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
}
