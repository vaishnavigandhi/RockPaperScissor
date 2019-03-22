package com.example.levantuan.finalproject_android;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class MapshowPlayer extends FragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase database;
    DatabaseReference root;
    private ChildEventListener mChildEventListener ;

    // permissions variables
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // map variables
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 30;


    // location variables
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;    // location of device
    private LocationCallback mLocationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show_player);
        // firebase
        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // ask for permission

        getLocationPermission();
        getDeviceLocation();


        // map variable setup
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // MARK - Map UI functions
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Do other setup activities here too, as described elsewhere in this tutorial.
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        // Ask for permissions
        Log.d("Tuan", "is permission given? " + mLocationPermissionGranted);

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        Log.d("Tuan", "going to setup callback");
        mLocationCallback = setupLocationCallback();

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

                        if (mChildEventListener == null) {

                            mChildEventListener = new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    ShowUsers users = dataSnapshot.getValue(ShowUsers.class);
                                    String name = users.getName();
                                    String txt = "Points:" + " " + users.getPoint() + "\n "+ "Lat:"  + " " + users.getLg() + "\n "+ "Long:"  + " " + users.getLat();


                                    // create a default map marker and put it here
                                    LatLng sydney = new LatLng(new Double(users.getLat()), new Double(users.getLg()));
                                    mMap.addMarker(new MarkerOptions().position(sydney)
                                            .title(users.getName()));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, DEFAULT_ZOOM));


                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            root.child("Users").addChildEventListener(mChildEventListener);
                        }
//                            Log.d("Tuan", "Current location is null. Using defaults.");
//                            Log.e("Tuan", "Exception: %s", task.getException());
//
//                            // create a default map marker and put it here
//                            LatLng sydney = new LatLng(-43.761539, 43.761539);
//                            mMap.addMarker(new MarkerOptions().position(sydney)
//                                .title("Marker in Sydney"));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, DEFAULT_ZOOM));
//
//                            //mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
                    }
                });
            }
            else {
                Log.d("Tuan", "getDeviceLocation - permission denied, not doing anything!");
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
            Log.d("Tuan", "getLocationPermission - Permission Granted");
            mLocationPermissionGranted = true;
        } else {
            Log.d("Tuan", "getLocationPermission - Permission Denied");
            ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        Log.d("Tuan", "inside onRequestPermissionResult");
        Log.d("Tuan", String.valueOf(requestCode));
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






}
