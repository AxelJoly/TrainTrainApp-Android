package fr.isen.traintrain.traintrainapp.Task;

/**
 * Created by maxim on 13/03/2018.
 */

import android.provider.BaseColumns;



public final class sqliteSave {


    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME_JOURNEY = "journey";
        public static final String COLUMN_NAME_ID_DEPART = "id_depart";
        public static final String COLUMN_NAME_ID_ARRIVEE = "id_arrivee";
        public static final String COLUMN_NAME_DEPART = "name_depart";
        public static final String COLUMN_NAME_ARRIVEE = "name_arrivee";
        public static final String COLUMN_NAME_LATITUDE_DEPART = "latitude_depart";
        public static final String COLUMN_NAME_LATITUDE_ARRIVEE = "latitude_arrivee";
        public static final String COLUMN_NAME_LONGITUDE_DEPART = "longitude_depart";
        public static final String COLUMN_NAME_LONGITUDE_ARRIVEE = "longitude_arrivee";
    }





    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_JOURNEY + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_NAME_ID_ARRIVEE + " VARCHAR(32)," +
                    FeedEntry.COLUMN_NAME_ID_DEPART + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_DEPART + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_ARRIVEE + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_LATITUDE_DEPART + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_LATITUDE_ARRIVEE + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_LONGITUDE_DEPART + " VARCHAR(32),"+
                    FeedEntry.COLUMN_NAME_LONGITUDE_ARRIVEE + " VARCHAR(32))";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME_JOURNEY;


}

