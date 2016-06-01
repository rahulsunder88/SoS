package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Emergency Activity for Rescuing
*/
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Emergency extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int countLocations =0;
    TextView name;
    private List<Marker> markerList;
    ArrayList<LatLng> markerPoints;
    Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    MediaPlayer mMediaPlayer = new MediaPlayer();
    RadioGroup rgName;
    RadioButton selectedRadioButton;

    double radiusInMeters = 3000.0;
    //red outline
    int strokeColor = 0xffff0000;
    //opaque red fill
    int shadeColor = 0x44ff0000;
    CircleOptions circleOptions;
    Circle mCircle;

    public Emergency() {
        if (markerList == null) {
            markerList = new ArrayList<Marker>();
        }
        if (markerPoints == null) {
            markerPoints = new ArrayList<LatLng>();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        name = (TextView) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        rgName = (RadioGroup)findViewById(R.id.rgName);
        rgName.setVisibility(View.INVISIBLE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Get Accomodation
        Button get_Accomodation = (Button) findViewById(R.id.getAccomodation);
        View.OnClickListener getAccomodation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                getLocation(mMap);
                // Toast.makeText(getBaseContext(), "get all locations inside" , Toast.LENGTH_SHORT).show();
            }
        };

        // Setting button click event listener for the find button
        get_Accomodation.setOnClickListener(getAccomodation);

        // Clear Map
        Button clear_Map = (Button) findViewById(R.id.clear);
        View.OnClickListener findClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                mMap.clear();
                name = (TextView) findViewById(R.id.name);
                name.setVisibility(View.INVISIBLE);
                rgName = (RadioGroup)findViewById(R.id.rgName);
                rgName.setVisibility(View.INVISIBLE);
                //  markerPoints.clear();
            }
        };

        // Setting button click event listener for the find button
        clear_Map.setOnClickListener(findClickListener1);

        // End of Clear Map

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng point) {

               // Toast.makeText(getBaseContext(), "inside CLICK LISTENER " , Toast.LENGTH_SHORT).show();

                mMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)));
                name = (TextView) findViewById(R.id.name);
                name.setVisibility(View.VISIBLE);

                rgName = (RadioGroup)findViewById(R.id.rgName);
                rgName.setVisibility(View.VISIBLE);

                // Clear Map
                Button clear_Map = (Button) findViewById(R.id.clear);
                View.OnClickListener findClickListener1 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        mMap.clear();
                       name = (TextView) findViewById(R.id.name);
                        name.setVisibility(View.INVISIBLE);
                        rgName = (RadioGroup)findViewById(R.id.rgName);
                        rgName.setVisibility(View.INVISIBLE);
                      //  markerPoints.clear();
                    }
                };

                // Setting button click event listener for the find button
                clear_Map.setOnClickListener(findClickListener1);

                // End of Clear Map

                // Save Accomodation
                Button save_Accomodation = (Button) findViewById(R.id.saveAccomodation);
                saveAccOnClickListener saveAcc = new saveAccOnClickListener( point)
                {
                    @Override
                    public void onClick(View v) {
                        // Start Ringtone
                        saveLocation(point,googleMap);

                        try {

                            mMediaPlayer.setDataSource(getApplicationContext(), alert);
                            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                           // if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0 | audioManager.getStreamVolume(AudioManager.STREAM_RING) == 0) {
                                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                               // mMediaPlayer.reset();
                                mMediaPlayer.setLooping(true);
                                mMediaPlayer.prepare();
                            //    Toast.makeText(getBaseContext(), "Inside try" , Toast.LENGTH_SHORT).show();
                                mMediaPlayer.start();
                          //  }
                        } catch(Exception e) {
                            //Toast.makeText(getBaseContext(), "inside exception"+e.toString() , Toast.LENGTH_SHORT).show();
                            //Log.d("Exception while ","Exception in Saving" +e.toString());
                            mMediaPlayer.stop();
                            mMediaPlayer.start();
                            mMediaPlayer.reset();
                            MediaPlayer mMediaPlayer = new MediaPlayer();

                            mMediaPlayer.reset();
                         //   Toast.makeText(getBaseContext(), "inside catch" , Toast.LENGTH_SHORT).show();
                            mMediaPlayer.start();
                        }

                        // End ringtone
                    }
                };

               //new_code_fix_durga_included
                save_Accomodation.setOnClickListener(saveAcc);


                // Clear Map
                Button Stop_Alarm = (Button) findViewById(R.id.stopAlarm);
                View.OnClickListener findClickListener2 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mMediaPlayer.stop();
                        mMediaPlayer.reset();

                        mCircle.remove();

                        Toast.makeText(getBaseContext(),  "Closed " , Toast.LENGTH_SHORT).show();
                    }
                };

                // Setting button click event listener for the find button
                Stop_Alarm.setOnClickListener(findClickListener2);

                // End of Clear Map


                // Setting button click event listener for the find button
                ////new_code_fix_durga_commented
                //save_Accomodation.setOnClickListener(saveAcc);

                // End of Save AccomodatioN

                //
                // End of Delete Emergency
                // Get Accomodation
                Button get_Accomodation = (Button) findViewById(R.id.getAccomodation);
                View.OnClickListener getAccomodation = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        getLocation(mMap);

                    }
                };

                // Setting button click event listener for the find button
                get_Accomodation.setOnClickListener(getAccomodation);
                System.out.println(point.latitude+"---"+ point.longitude);
            }

        });
        googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    boolean doNotMoveCameraToCenterMarker = true;
                    public boolean onMarkerClick(final Marker marker ) {
                        //Do whatever you need to do here ....
                        marker.showInfoWindow();
                        //   marker.setVisible(false);
                      //      Toast.makeText(getBaseContext(), "inside marker click " , Toast.LENGTH_SHORT).show();
                        LatLng myLatLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                        Button delete_Accomodation = (Button) findViewById(R.id.deleteAccomodation);
                        delAccOnClickListener delAcc = new delAccOnClickListener( myLatLng, marker)
                        {
                            @Override
                            public void onClick(View v) {
                                // Start NewActivity.class
                              //  Toast.makeText(getBaseContext(), "inside marker click one " , Toast.LENGTH_SHORT).show();
                                AlertDialog alertDialog = new AlertDialog.Builder(Emergency.this).create();
                                alertDialog.setTitle("Delete Marker");
                                alertDialog.setMessage("Want To Delete Marker?");
                                alertDialog.setButton("Delete",new DialogInterface.OnClickListener()
                                {
                                    @SuppressLint({ "ShowToast", "NewApi" })
                                    public void onClick(final DialogInterface dialog,final int which)
                                    {

                                        getKey(mMap, delAcc.latitude,delAcc.longitude);
                                        // Below two lines to delete marker
                                        mMap.clear();
                                        getLocation(mMap);

                                    }
                                });
                                alertDialog.setButton2("Cancel",new DialogInterface.OnClickListener()
                                {
                                    @SuppressLint("ShowToast")
                                    public void onClick(final DialogInterface dialog,final int which)
                                    {

                                        Toast.makeText(getApplicationContext(),"Cancelled",1000).show();
                                    }
                                });

                                alertDialog.show();


                            }
                        };

                        //return true;
                        // Setting button click event listener for the find button
                        delete_Accomodation.setOnClickListener(delAcc);
                        //

                        return doNotMoveCameraToCenterMarker;
                    }
                });
    }

    private void getKey( GoogleMap googleMap, double Mylatitude, double Mylongitude){
        int mylocation =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);

        //  Toast.makeText(getBaseContext(), "inside get key " , Toast.LENGTH_SHORT).show();
        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("Emergency", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("countLocations", 0);
      //   Toast.makeText(getBaseContext(),  "INSIDE GET KEY " , Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            String name;
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 1; i <= mylocation; i++) {

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat" + i, "0");
                latitude = Double.parseDouble(lat);
                //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng" + i, "0");
              //   Toast.makeText(getBaseContext(), "the lat is INSIDE GET KEY" + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);

                name = sharedPreferences.getString("name" + i, "0");
                // Drawing marker on the map
             /*   MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("New Marker");

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));*/
                if (Mylatitude == latitude && Mylongitude == longitude)
                {
                    Toast.makeText(getBaseContext(), "Marker Deleted " , Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().remove("lat" + i).commit();
                    sharedPreferences.edit().remove("lng" + i).commit();
                    sharedPreferences.edit().remove("name" + i).commit();

                }
            }

        }
        //  editor.commit();
    }

    private void getLocation( GoogleMap googleMap){
        int mylocation =0;
        String name;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);


        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("Emergency", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("countLocations", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 1; i <= mylocation; i++) {

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat" + i, "0");
                latitude = Double.parseDouble(lat);
                //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng" + i, "0");

                name = sharedPreferences.getString("name" + i, "0");
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);
                // Drawing marker on the map
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("Contact Details").snippet(name);

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));
            }

        }
        //  editor.commit();
    }


    private void saveLocation(LatLng point, GoogleMap googleMap){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);

        name = (TextView) findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);

        rgName = (RadioGroup)findViewById(R.id.rgName);
        rgName.setVisibility(View.VISIBLE);

        sharedPreferences = getSharedPreferences("Emergency", 0);
        countLocations = sharedPreferences.getInt("countLocations", 0);
        //
        // Toast.makeText(getBaseContext(), "no of locations is " + noofLocations, Toast.LENGTH_SHORT).show();
        countLocations++ ;
        /** Opening the editor object to write data to sharedPreferences */
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Storing the latitude for the i-th location
        editor.putString("lat"+ Integer.toString((countLocations)), Double.toString(point.latitude));

        // Storing the longitude for the i-th location
        editor.putString("lng"+ Integer.toString((countLocations)), Double.toString(point.longitude));

        // Storing the name
        rgName = (RadioGroup) findViewById(R.id.rgName);
        int selectedId = rgName.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedId);
        editor.putString("name"+ Integer.toString((countLocations)), selectedRadioButton.getText().toString());

        // Storing the count of locations or marker count
        editor.putInt("countLocations", countLocations);

        /** Storing the zoom level to the shared preferences */
        editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));

        /** Saving the values stored in the shared preferences */
        editor.commit();
        String lat = sharedPreferences.getString("lat" + (countLocations), "0");
        Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getBaseContext(), "The lat is " + countLocations, Toast.LENGTH_SHORT).show();
        googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)));


        circleOptions = new CircleOptions().center(point).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(2);
         mCircle = googleMap.addCircle(circleOptions);

        name = (TextView) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);
        rgName = (RadioGroup)findViewById(R.id.rgName);
        rgName.setVisibility(View.INVISIBLE);
    }

    public class saveAccOnClickListener implements View.OnClickListener
    {

        LatLng saveAcc;
        public saveAccOnClickListener(LatLng saveAcc) {
            this.saveAcc = saveAcc;
        }

        @Override
        public void onClick(View v)
        {
            //read your lovely variable
        }

    };
    public class directionsAccOnClickListener implements View.OnClickListener
    {

        LatLng getDir;
        public directionsAccOnClickListener(LatLng getDir) {

            this.getDir = getDir;
        }

        @Override
        public void onClick(View v)
        {
            //read your lovely variable
        }

    };
    public class delAccOnClickListener implements View.OnClickListener
    {

        LatLng delAcc;
        Marker delmark;
        public delAccOnClickListener(LatLng delAcc, Marker delmark) {
            this.delAcc = delAcc;
            this.delmark = delmark;
        }

        @Override
        public void onClick(View v)
        {
            //read your lovely variable
        }

    };
}
