package fr.isen.traintrain.traintrainapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapter;
import fr.isen.traintrain.traintrainapp.Entity.Station;
import fr.isen.traintrain.traintrainapp.Entity.Trip;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

public class FavorisActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<String> list = new ArrayList<String>();
    protected ArrayList<Trip> trips = new ArrayList<Trip>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        Intent thisIntent =getIntent();
        //users=(ArrayList<User2>)thisIntent.getExtras().get("users");

        Log.d("my adapter","constructor");
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                sqliteSave.FeedEntry.COLUMN_NAME_DEPART,
                sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE,
                sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE,
                sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART,
                sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE,
                sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART,
                sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART,
                sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE
        };

// Filter results WHERE "title" = 'My Title'
      /*  String selection = sqliteSave.FeedEntry.COLUMN_NAME_DEPART + " = ?";
        String[] selectionArgs = {  };*/

// How you want the results sorted in the resulting Cursor
        /*String sortOrder =
                sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE + " DESC";*/

        Cursor cursor = db.query(
                sqliteSave.FeedEntry.TABLE_NAME_JOURNEY,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null             // The sort order
        );




        while(cursor.moveToNext()) {


            Log.d("name = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_DEPART)));
            Log.d("name arrivee = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE)));
            Log.d("id depart = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART)));
            Log.d("id arrivee = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE)));
            Log.d("longitude depart = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART)));
            Log.d("longitude arrivee = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE)));
            Log.d("latitude depart = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART)));
            Log.d("latitude arrivee = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE)));


            this.trips.add(new Trip(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE))));



        }
        cursor.close();


        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this,this.trips);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
