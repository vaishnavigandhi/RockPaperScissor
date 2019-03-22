package com.example.levantuan.finalproject_android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference root;

    //location
    // permissions variables
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // map variables
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 15;


    // location variables
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;    // location of device
    //end


    TextView Name,Point;
    Button create,join;


    private final List<Users> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // firebase
        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        Name = findViewById(R.id.Nametxt);
        Point = findViewById(R.id.pointtxt);

        final String getname = getIntent().getExtras().getString("username");
        System.out.print(getname);

        Name.setText(getname);

        attachChatMessagesReadListener();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // ask for permission
        Log.d("JENELLE", "onCreate - asking for permissions");
        getLocationPermission();
        getDeviceLocation();



        create = (Button)findViewById(R.id.create);
        join = (Button)findViewById(R.id.join);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, createPageActivity.class);
                // pass name
                intent.putExtra("getName",getname);
                startActivity(intent);

                DatabaseReference myRef = database.getReference("status");

                myRef.setValue("waiting");



            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main2Activity.this, joinpage.class);
                startActivity(intent);
            }
        });



    }
    //getting particular value by identify User name;
    private void attachChatMessagesReadListener() {

            //get user name.
            String getname = getIntent().getExtras().getString("username");

            //get value from firebase

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mostafa = ref.child("Users").child(getname).child("point");

            mostafa.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String point = dataSnapshot.getValue(String.class);
                    //do what you want with the email
                    Point.setText(point + "Points");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }

        //button
    public void getPlayersPressed(View view) {
        final String getname = getIntent().getExtras().getString("username");
        Intent myIntent = new Intent(Main2Activity.this, TableViewPlayers.class);
        myIntent.putExtra("getName",getname);
        startActivity(myIntent);

    }


    private LocationCallback setupLocationCallback() {
        LocationCallback mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d("Tuan", "+++++ CALLING CALLBACK");
                for (Location location : locationResult.getLocations()) {
                    Log.d("Tuan", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    mLastKnownLocation = location;

                    // @TODO: create a global variable to track the current marker
                    /*
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    */

                    // @TODO: remove this when you're finished
                    mMap.clear();

                    //Place current location marker
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    //mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                    mMap.addMarker(markerOptions);

                    //move map camera
                    //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                }
            };

        };

        return mLocationCallback;
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                Log.d("Tuan", "Permission granted, showing MyLocation button");
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                Log.d("Tuan", "Permission denied, ask for permission again");
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Log.d("Tuan", "getDeviceLocation - got permission");
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();

                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {

                            Log.d("Tuan", "Got last known location");

                            mLastKnownLocation = task.getResult();

                            // Set the map's camera position to the current location of the device.
                            if (mLastKnownLocation == null) {
                                Log.d("Tuan", "Last known location is null");
                                Toast.makeText(Main2Activity.this, "Location is null", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                String getname = getIntent().getExtras().getString("username");
                                String loc = String.valueOf(mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude());
                                Toast.makeText(Main2Activity.this, loc , Toast.LENGTH_SHORT).show();
                                Log.d("Tuan", mLastKnownLocation.toString());
                                root.child("Users").child(getname).child("lat").setValue(Double.toString(mLastKnownLocation.getLatitude()));
                                root.child("Users").child(getname).child("lg").setValue(Double.toString(mLastKnownLocation.getLongitude()));

                                // put a marker on your current location
                                LatLng currentPosition = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//
                            }


                        } else {
                            Log.d("Tuan", "Current location is null. Using defaults.");
                            Log.e("Tuan", "Exception: %s", task.getException());

                            // create a default map marker and put it here
                            LatLng sydney = new LatLng(-33.87365, 151.20689);
//
                        }
                    }
                });
            }
            else {
                Log.d("JENELLE", "getDeviceLocation - permission denied, not doing anything!");
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    // MARK - Permissions functions
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("JENELLE", "getLocationPermission - Permission Granted");
            mLocationPermissionGranted = true;
        } else {
            Log.d("JENELLE", "getLocationPermission - Permission Denied");
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        Log.d("JENELLE", "inside onRequestPermissionResult");
        Log.d("JENELLE", String.valueOf(requestCode));
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

        //TODO: add this functions
        updateLocationUI();
    }

    public void NearByPlayersPressed(View view) {
        Intent myIntent = new Intent(Main2Activity.this, MapshowPlayer.class);
        startActivity(myIntent);

    }

    public void playBotPressed(View view) {
        Intent myIntent = new Intent(Main2Activity.this, ChallegeBots.class);
        startActivity(myIntent);
    }
}
