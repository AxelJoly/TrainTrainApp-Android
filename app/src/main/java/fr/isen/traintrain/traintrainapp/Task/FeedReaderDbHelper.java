package fr.isen.traintrain.traintrainapp.Task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static fr.isen.traintrain.traintrainapp.Task.sqliteSave.SQL_CREATE_ENTRIES;
import static fr.isen.traintrain.traintrainapp.Task.sqliteSave.SQL_CREATE_ENTRIES2;
import static fr.isen.traintrain.traintrainapp.Task.sqliteSave.SQL_DELETE_ENTRIES;
import static fr.isen.traintrain.traintrainapp.Task.sqliteSave.SQL_DELETE_ENTRIES2;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "traintrain.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("feedreader","constructor");

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.d("feedreader","on upgarde");
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
