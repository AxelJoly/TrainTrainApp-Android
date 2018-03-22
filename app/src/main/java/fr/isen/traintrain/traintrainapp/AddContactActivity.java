package fr.isen.traintrain.traintrainapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.isen.traintrain.traintrainapp.Entity.Contact;
import fr.isen.traintrain.traintrainapp.Task.FeedReaderDbHelper;
import fr.isen.traintrain.traintrainapp.Task.sqliteSave;

import static android.Manifest.permission.READ_CONTACTS;

public class AddContactActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int REQUEST_READ_CONTACTS = 444;

    private ListView mListView;
    private ProgressDialog pDialog;
    private Handler updateBarHandler;
    protected ImageView imageView ;

    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    static final int REQUEST_IMAGE_CAPTURE = 8;
    private int count;

    TextView latitude, longitude;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    protected FeedReaderDbHelper mDbHelper;
    protected ArrayList<Contact> contacts = new ArrayList<Contact>();


    ArrayList<String> contactList;
    Cursor cursor;
    int counter;

    @Override
    @TargetApi(Build.VERSION_CODES.M)


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Ajout contact");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mListView = (ListView) findViewById(R.id.contactListe);
        mDbHelper = new FeedReaderDbHelper(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Reading contacts...");
        pDialog.setCancelable(false);
        pDialog.show();



        updateBarHandler = new Handler();

        permissionsToRequest = findUnAskedPermissions(permissions);


        // Since reading contacts takes more time, let's run it on a separate thread.


        // Set onclicklistener to the list item.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //TODO Do whatever you want with the list data


                SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(sqliteSave.FeedEntry.COLUMN_NAME_CONTACT, contacts.get(position).getNameContact());
                values.put(sqliteSave.FeedEntry.COLUMN_NAME_PHONE_NUMBER, contacts.get(position).getPhoneNumber());


// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(sqliteSave.FeedEntry.TABLE_NAME_CONTACT, null, values);
                Toast.makeText(getApplicationContext(), "Contact : \n" + contacts.get(position).getNameContact() + " ajouté", Toast.LENGTH_SHORT).show();
            }
        });


        new Thread(new Runnable() {

            @Override
            public void run() {
                getContacts();
            }
        }).start();
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
            Intent intent =new Intent(this,TravelActivity.class);

            this.startActivity(intent);
        } else if (id == R.id.nav_favoris) {

            Log.d("main activity","favoris");
            Intent intent =new Intent(this,FavorisActivity.class);

            this.startActivity(intent);

        }

        else if(id == R.id.nav_recherche_contact){
            Intent intent =new Intent(this,ShowContactActivity.class);

            this.startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */


    public void getContacts() {

        while (!mayRequestContacts()) {

        }

        if (!mayRequestContacts()) {
            return;
        }
        else{
            contactList = new ArrayList<String>();

            String phoneNumber = null;
            String phoneNumberBefore = null;
            String email = null;

            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            String _ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

            Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

            Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
            String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
            String DATA = ContactsContract.CommonDataKinds.Email.DATA;

            StringBuffer output;

            ContentResolver contentResolver = getContentResolver();

            cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

            // Iterate every contact in the phone
            if (cursor.getCount() > 0) {

                counter = 0;
                while (cursor.moveToNext()) {
                    output = new StringBuffer();
                    Contact contact = new Contact();

                    // Update the progress message
                    updateBarHandler.post(new Runnable() {
                        public void run() {
                            pDialog.setMessage("Lecture des contacts : " + counter++ + "/" + cursor.getCount());
                        }
                    });

                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                    if (hasPhoneNumber > 0 && name!=null) {
                        output.append("\n Nom: " + name);
                        contact.setNameContact(name);

                        //This is to read multiple phone numbers associated with the same contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                        phoneCursor.moveToNext();
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Numéro de télephone: " + phoneNumber);
                        contact.setPhoneNumber(phoneNumber);
                 /* count=0;
                   while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        if (count!=0) {
                            phoneCursor.moveToLast();
                            phoneNumberBefore = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            phoneCursor.moveToNext();
                            if (phoneNumber!=phoneNumberBefore) {

                                output.append("\n Numéro de télephone: " + phoneNumber);
                                contact.setPhoneNumber(phoneNumber);
                            }
                        }else {
                            output.append("\n Numéro de télephone: " + phoneNumber);
                            contact.setPhoneNumber(phoneNumber);
                        }


                        count++;

                    }*/

                        phoneCursor.close();

                /*    // Read every email id associated with the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                        output.append("\n Email: " + email);

                    }

                    emailCursor.close();

                    String columns[] = {
                            ContactsContract.CommonDataKinds.Event.START_DATE,
                            ContactsContract.CommonDataKinds.Event.TYPE,
                            ContactsContract.CommonDataKinds.Event.MIMETYPE,
                    };

                    String where = ContactsContract.CommonDataKinds.Event.TYPE + "=" + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY +
                            " and " + ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' and " + ContactsContract.Data.CONTACT_ID + " = " + contact_id;

                    String[] selectionArgs = null;
                    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;

                    Cursor birthdayCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, columns, where, selectionArgs, sortOrder);
                    Log.d("BDAY", birthdayCur.getCount()+"");
                    if (birthdayCur.getCount() > 0) {
                        while (birthdayCur.moveToNext()) {
                            String birthday = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                            output.append("Birthday :" + birthday);
                            Log.d("BDAY", birthday);
                        }
                    }
                    birthdayCur.close();   */
                    }

                    // Add the contact to the ArrayList
                    contactList.add(output.toString());
                    this.contacts.add(contact);
                }

                // ListView has to be updated using a ui thread
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, contactList);
                        mListView.setAdapter(adapter);
                    }
                });

                // Dismiss the progressbar after 500 millisecondds
                updateBarHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pDialog.cancel();
                    }
                }, 500);
            }
        }



    }







    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    // getLocation();
                }
                break;
        }
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
