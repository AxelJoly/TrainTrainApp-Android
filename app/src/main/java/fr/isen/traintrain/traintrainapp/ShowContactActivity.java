package fr.isen.traintrain.traintrainapp;

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

import fr.isen.traintrain.traintrainapp.Adapter.MyAdapterContact;
import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

public class ShowContactActivity extends AppCompatActivity {

    protected ArrayList<Contact> contacts = new ArrayList<Contact>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

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


            Log.d("name = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT)));
            Log.d("phone number = ",cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER)));



            //  this.trips.add(new Trip(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ID_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_DEPART)),cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE))));

            contact.setNameContact(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER)));
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
}