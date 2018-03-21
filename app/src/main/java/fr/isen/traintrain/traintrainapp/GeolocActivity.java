package fr.isen.traintrain.traintrainapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.Iterator;
import java.util.ListIterator;

import fr.isen.traintrain.traintrainapp.Adapter.GeolocAdapter;
import fr.isen.traintrain.traintrainapp.AsyncTask.StationAsyncTask;
import fr.isen.traintrain.traintrainapp.Entity.AsyncResponse;
import fr.isen.traintrain.traintrainapp.Entity.Geoloc;
import fr.isen.traintrain.traintrainapp.Entity.Station;

public class GeolocActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener, AsyncResponse {


    final String TAG = "GPS";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private static final int REQUEST_READ_CONTACTS = 444;

    protected ProgressDialog progressDialog;
    protected ArrayList<Station> stations = new ArrayList<Station>();
    protected ListIterator<Station> itr;
    protected    ArrayList<Station> stationsFiltered = new ArrayList<Station>();
    protected ListIterator<Station> itrFiltered;
    protected ArrayList<Geoloc> geoloc;
    protected ListIterator<Geoloc> itrGeoloc;

    Cursor cursor;
    int counter;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geoloc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Géolocalisation");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Load");
        progressDialog.show();

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

            // get location
            getLocation();
        }
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
        getMenuInflater().inflate(R.menu.geoloc, menu);
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
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);


                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);


                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
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
                    getLocation();
                }
                break;

        }

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(GeolocActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        // this.latitude.setText(Double.toString(loc.getLatitude()));
        // this.longitude.setText(Double.toString(loc.getLongitude()));
        //tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));

        Log.d("Location: ", loc.getLongitude() + " " + loc.getLatitude());
        this.findClosestStations(loc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void findClosestStations(Location station){
        StationAsyncTask ipinf = new StationAsyncTask(progressDialog, this);
        ipinf.delegate = this;
        ipinf.execute();


    }



    @Override
    public void processFinish(ArrayList<Station> output) {

        Log.d("activity","lol");

        this.stations = output;
        System.out.println("Ca passe");
        this.itr = this.stations.listIterator();
        this.itrFiltered = this.stationsFiltered.listIterator();
        this.geoloc = new ArrayList<Geoloc>();
        this.itrGeoloc = this.geoloc.listIterator();

        Double lat1 = this.loc.getLatitude();
        Double lon1 = this.loc.getLongitude();
        Log.d("", lon1 + ";" + lat1);
        haversine(lat1, lon1);
    }


    public void haversine(Double lat1, Double lon1){
        int index = 0;



        while (itr.hasNext()) {

            Station temp = this.itr.next();
            Double lat2 = Double.parseDouble(temp.getStop_lat());
            Double lon2 = Double.parseDouble(temp.getStop_lon());

            int R = 6371; // km
            Double dLat = this.toRad(lat2 - lat1);
            Double dLon = this.toRad(lon2 - lon1);
            Double rlat1 = this.toRad(lat1);
            Double rlat2 = this.toRad(lat2);

            Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(rlat1) * Math.cos(rlat2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double d = R * c;

            if (d < 10) {

                boolean repeated = false;

                for (Geoloc _c: this.geoloc) {
                    if(_c.getStation().getName().equals(temp.getName()))
                        repeated = true;
                }
                if(!repeated) {
                    // this.stationsFiltered.add(temp);
                    this.geoloc.add(new Geoloc(temp, Double.toString(d)));
                    Log.d("Value", temp.getName());
                }
            }
            //this.stationsFiltered.add(temp);
            //                        this.distance.add(Double.toString(d));
            //Log.d("INFO", "value of index " + index + ": " + d + "arret:" + temp.getName());

            index++;



        }

        mRecyclerView = (RecyclerView) findViewById(R.id.listGeoloc);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new GeolocAdapter(this.geoloc, this.getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);


    }





    public Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public ArrayList<Station> deleteDuplicate(ArrayList<Station> arrayToClean){
        ArrayList<Station> l2 = new ArrayList<Station>();

        Iterator iterator1 = arrayToClean.iterator();
        boolean repeated = false;

        while (iterator1.hasNext())
        {
            Station c1 = (Station) iterator1.next();
            for (Station _c: l2) {
                if(_c.getName().equals(c1.getName()))
                    repeated = true;
            }
            if(!repeated) {
                l2.add(c1);
                Log.d("Value", c1.getName());
            }
        }
        return l2;
    }

    public void initRecycleView(){

    }
}
