package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Activity class for Co-ordinating help offers and help requests for accommodation during disaster times.
*/
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Accomodation extends FragmentActivity implements OnMapReadyCallback {
    private RadioGroup needprovidegroup;
    private RadioButton needprovide;
    private RadioButton need;
    private RadioButton provide;
    private GoogleMap mMap;
    int noofLocations = 0;
    EditText phoneNumber;
    EditText name;
    String type1;
    // latitude and longitude
    //double latitude = ;
    //double longitude = ;
    private List<Marker> markerList;
    ArrayList<LatLng> markerPoints;

    public Accomodation()
    {
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
        setContentView(R.layout.activity_accomodation);

        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.INVISIBLE);

        needprovidegroup = (RadioGroup) findViewById(R.id.needprovide);
        need = (RadioButton) findViewById(R.id.need);
        need.setVisibility(View.INVISIBLE);

        provide = (RadioButton) findViewById(R.id.provide);
        provide.setVisibility(View.INVISIBLE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapAccomodation = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapAccomodation.getMapAsync(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapAccomodation.getMapAsync(this);
        // Getting Map for the SupportMapFragment
        mMap = mapAccomodation.getMap();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        View.OnClickListener clearClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                System.out.println("Clearing");
                mMap.clear();
                markerPoints.clear();
                name = (EditText) findViewById(R.id.name);
                name.setVisibility(View.INVISIBLE);

                phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                phoneNumber.setVisibility(View.INVISIBLE);

                need = (RadioButton) findViewById(R.id.need);
                need.setVisibility(View.INVISIBLE);

                provide = (RadioButton) findViewById(R.id.provide);
                provide.setVisibility(View.INVISIBLE);
            }
        };
        clear_Map.setOnClickListener(clearClickListener1);

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

        // Add a marker in Chennai and move the camera
        LatLng chennai = new LatLng(13, 80);
        mMap.addMarker(new MarkerOptions().position(chennai).title("SOS's Sample Marker in Chennai"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chennai));
        //  btn_find.setOnClickListener(findClickListener);



        // End of Save Accomodation
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            // Add a marker in Sydney and move the camera
            //  LatLng sydney = new LatLng(-34, 151);

            @Override
            public void onMapClick(LatLng point) {
                //  saveLocation(point , googleMap);
                name = (EditText) findViewById(R.id.name);
                name.setVisibility(View.VISIBLE);

                phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                phoneNumber.setVisibility(View.VISIBLE);


                need = (RadioButton) findViewById(R.id.need);
                need.setVisibility(View.VISIBLE);

                provide = (RadioButton) findViewById(R.id.provide);
                provide.setVisibility(View.VISIBLE);

                if(markerPoints.size()>1){
                    markerPoints.clear();

                    mMap.clear();
                }
                markerPoints.add(point);
                MarkerOptions options = new MarkerOptions();
                options.position(point);

                if(markerPoints.size()==1){

                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                }else if(markerPoints.size()==2){

                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                }
                mMap.addMarker(options);
                //  mMap.addMarker(new MarkerOptions().position(point).title("Added"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                //Clear Map start




                // Clear Map
                Button clear_Map = (Button) findViewById(R.id.clear);
                View.OnClickListener clearClickListener1 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        System.out.println("Clearing");
                        googleMap.clear();
                        markerPoints.clear();
                        name = (EditText) findViewById(R.id.name);
                        name.setVisibility(View.INVISIBLE);

                        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                        phoneNumber.setVisibility(View.INVISIBLE);

                        need = (RadioButton) findViewById(R.id.need);
                        need.setVisibility(View.INVISIBLE);

                        provide = (RadioButton) findViewById(R.id.provide);
                        provide.setVisibility(View.INVISIBLE);
                    }
                };
                clear_Map.setOnClickListener(clearClickListener1);
                //Clear Map end
                // Save Accomodation
                Button save_Accomodation = (Button) findViewById(R.id.saveAccomodation);
                saveAccOnClickListener saveAcc = new saveAccOnClickListener( point)
                {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        saveLocation(saveAcc,googleMap);
                    }
                };


                // Setting button click event listener for the find button
                save_Accomodation.setOnClickListener(saveAcc);

                // End of Save Accomodation
                // Get Accomodation
                Button get_Accomodation = (Button) findViewById(R.id.getAccomodation);
                View.OnClickListener getAccomodation = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        getLocation(googleMap);

                    }
                };

                // Setting button click event listener for the find button
                get_Accomodation.setOnClickListener(getAccomodation);

                // Get Directions
                Button get_Directions = (Button) findViewById(R.id.getDirection);
                directionsAccOnClickListener getDir = new directionsAccOnClickListener(point)
                {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class

                        //getDirections(point, googleMap);
                        getDirections( googleMap);
                    }
                };


                // Setting button click event listener for the find button
                get_Directions.setOnClickListener(getDir);


                System.out.println(point.latitude+"---"+ point.longitude);

            }


        });
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    boolean doNotMoveCameraToCenterMarker = true;
                    public boolean onMarkerClick(final Marker marker ) {
                        //Do whatever you need to do here ....
                        marker.showInfoWindow();
                        LatLng point = marker.getPosition();

                        markerPoints.add(point);
                        MarkerOptions options = new MarkerOptions();
                        options.position(point);
                        //   marker.setVisible(false);
                        //    Toast.makeText(getBaseContext(), "inside marker click " , Toast.LENGTH_SHORT).show();
                        LatLng myLatLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                        Button delete_Accomodation = (Button) findViewById(R.id.deleteAccomodation);
                        delAccOnClickListener delAcc = new delAccOnClickListener( myLatLng, marker)
                        {
                            @Override
                            public void onClick(View v) {
                                // Start NewActivity.class
                                AlertDialog alertDialog = new AlertDialog.Builder(Accomodation.this).create();
                                alertDialog.setTitle("Delete Marker");
                                alertDialog.setMessage("Want To Delete Marker?");
                                alertDialog.setButton("Delete",new DialogInterface.OnClickListener()
                                {
                                    @SuppressLint({ "ShowToast", "NewApi" })
                                    public void onClick(final DialogInterface dialog,final int which)
                                    {

                                        getKey(googleMap, delAcc.latitude,delAcc.longitude);
                                        // Below two lines to delete marker
                                        googleMap.clear();
                                        getLocation(googleMap);

                                    }
                                });
                                alertDialog.setButton2("Cancel",new DialogInterface.OnClickListener()
                                {
                                    @SuppressLint("ShowToast")
                                    public void onClick(final DialogInterface dialog,final int which)
                                    {

                                        Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialog.show();


                            }
                        };

                        //return true;
                        // Setting button click event listener for the find button
                        delete_Accomodation.setOnClickListener(delAcc);
                        //
                        if (markerPoints.size() >= 2) {
                            // Get Directions
                            Button get_Directions = (Button) findViewById(R.id.getDirection);
                            directionsAccOnClickListener getDir = new directionsAccOnClickListener(point) {
                                @Override
                                public void onClick(View v) {
                                    // Start NewActivity.class

                                    //getDirections(point, googleMap);
                                    getDirections(googleMap);
                                }
                            };


                            // Setting button click event listener for the find button
                            get_Directions.setOnClickListener(getDir);
                        }

                        return doNotMoveCameraToCenterMarker;
                    }
                });

    }
    private void getLocation( GoogleMap googleMap){
        int mylocation =0;
        String key ;
        String name1;
        String phoneNumber1;
        String type1;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);


        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location1", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations1", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0

        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {
            // Toast.makeText(getBaseContext(), "get all locations inside" , Toast.LENGTH_SHORT).show();
            String lat1 = "";
            String lng1 = "";
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 1; i <= mylocation; i++) {
                key = sharedPreferences.getString("text" + i, "No");

                if (key.equals("Accomodation")) {
                    //   Toast.makeText(getBaseContext(), "text inside is " + key, Toast.LENGTH_SHORT).show();
                    // Getting the latitude of the i-th location
                    lat1 = sharedPreferences.getString("lat1" + i, "0");
                    latitude = Double.parseDouble(lat1);
                    //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                    // Getting the longitude of the i-th location
                    lng1 = sharedPreferences.getString("lng1" + i, "0");
                    // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                    name1 = sharedPreferences.getString("name1" + i, "0");
                    phoneNumber1 = sharedPreferences.getString("phonenumber1" + i, "0");
                    type1 = sharedPreferences.getString("type1" + i, "0");
                    longitude = Double.parseDouble(lng1);
                    // Drawing marker on the map
                    //     mMap.addMarker(new MarkerOptions().position(point).title("Added"));
                    //    MarkerOptions marker = new MarkerOptions().position(
                    //       new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type1);

                    //        googleMap.addMarker(marker);
                    //       googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Added"));
                    if (type1.equals("Need Service" ) ) {
                        //  googleMap.addMarker(marker);
                        googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name1 + " " + phoneNumber1 + " " + type1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }

                    else
                    {
                        //  googleMap.addMarker(marker);
                        googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name1 + " " + phoneNumber1 + " " + type1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        //mMap.addMarker(options);
                    }


                }
            }

        }
        //  editor.commit();
    }
    private void saveLocation(LatLng point, GoogleMap googleMap){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);
        String Accomodation = "Accomodation";

        sharedPreferences = getSharedPreferences("location1", 0);
        noofLocations = sharedPreferences.getInt("noofLocations1", 0);


        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.VISIBLE);

        need = (RadioButton) findViewById(R.id.need);
        need.setVisibility(View.VISIBLE);

        provide = (RadioButton) findViewById(R.id.provide);
        provide.setVisibility(View.VISIBLE);
        needprovidegroup = (RadioGroup) findViewById(R.id.needprovide);
        //
        // Toast.makeText(getBaseContext(), "no of locations is " + noofLocations, Toast.LENGTH_SHORT).show();
        noofLocations++ ;
        /** Opening the editor object to write data to sharedPreferences */
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Storing the latitude for the i-th location
        editor.putString("lat1"+ Integer.toString((noofLocations)), Double.toString(point.latitude));

        // Storing the longitude for the i-th location
        editor.putString("lng1"+ Integer.toString((noofLocations)), Double.toString(point.longitude));

        // Storing the Text Accomodation for the i-th location

        // Storing the name
        editor.putString("name1"+ Integer.toString((noofLocations)), name.getText().toString());

        // Storing the Phone number
        editor.putString("phonenumber1"+ Integer.toString((noofLocations)), phoneNumber.getText().toString());

        //Storing type

        int selectedId = needprovidegroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        needprovide = (RadioButton) findViewById(selectedId);

        Toast.makeText(getApplicationContext(),
                needprovide.getText(), Toast.LENGTH_SHORT).show();
        type1=needprovide.getText().toString();

        editor.putString("type1"+ Integer.toString((noofLocations)),  needprovide.getText().toString());

        editor.putString("text"+ Integer.toString((noofLocations)), Accomodation);
        // Toast.makeText(getBaseContext(), "text is  " + noofLocations, Toast.LENGTH_SHORT).show();
        // Storing the count of locations or marker count
        editor.putInt("noofLocations1", noofLocations);

        /** Storing the zoom level to the shared preferences */
        editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));

        /** Saving the values stored in the shared preferences */
        editor.commit();
        String text = sharedPreferences.getString("text" + (noofLocations), "no");
        // Toast.makeText(getBaseContext(), "text is " + text, Toast.LENGTH_SHORT).show();
        //  Toast.makeText(getBaseContext(), "The type1 is " + type1.getText().toString(), Toast.LENGTH_SHORT).show();
        // googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)));
        Toast.makeText(getBaseContext(), "Request Created"+needprovide.getText().toString(), Toast.LENGTH_SHORT).show();

        if (needprovide.getText().toString().equals("Need Service")) {
            // googleMap.addMarker(marker);
            googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        else
        {
            //googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)));
            googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            //mMap.addMarker(options);
        }

        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.INVISIBLE);

        need = (RadioButton) findViewById(R.id.need);
        need.setVisibility(View.INVISIBLE);

        provide = (RadioButton) findViewById(R.id.provide);
        provide.setVisibility(View.INVISIBLE);
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
    private void getKey( GoogleMap googleMap, double Mylatitude, double Mylongitude){
        int mylocation =0;
        String key;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);

        //  Toast.makeText(getBaseContext(), "inside get key " , Toast.LENGTH_SHORT).show();
        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location1", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations1", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat1 = "";
            String lng1 = "";
            String name1;
            String phoneNumber1;
            String type1;
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 1; i <= mylocation; i++) {
                key = sharedPreferences.getString("text" + i, "No");
                // Toast.makeText(getBaseContext(), "Marker  " + key, Toast.LENGTH_SHORT).show();
                if (key.equals("Accomodation")) {
                    // Getting the latitude of the i-th location
                    lat1 = sharedPreferences.getString("lat1" + i, "0");
                    latitude = Double.parseDouble(lat1);
                    //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                    // Getting the longitude of the i-th location
                    lng1= sharedPreferences.getString("lng1" + i, "0");
                    // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                    longitude = Double.parseDouble(lng1);
                    // Drawing marker on the map

                    name1 = sharedPreferences.getString("name1" + i, "0");
                    phoneNumber1 = sharedPreferences.getString("phonenumber1" + i, "0");
                    type1 = sharedPreferences.getString("type1" + i, "0");
                 /*   MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(latitude, longitude)).title("New Marker");


                    googleMap.addMarker(marker);
                    googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));*/
                    if (Mylatitude == latitude && Mylongitude == longitude) {
                        Toast.makeText(getBaseContext(), "Request Closed ", Toast.LENGTH_SHORT).show();
                        sharedPreferences.edit().remove("lat1" + i).commit();
                        sharedPreferences.edit().remove("lng1" + i).commit();
                        sharedPreferences.edit().remove("name1" + i).commit();
                        sharedPreferences.edit().remove("type1" + i).commit();
                        sharedPreferences.edit().remove("phonenumber1" + i).commit();

                    }
                }
            }

        }
        //  editor.commit();
    }

    void getDirections(GoogleMap googleMap)
    {

        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.INVISIBLE);

        need = (RadioButton) findViewById(R.id.need);
        need.setVisibility(View.INVISIBLE);

        provide = (RadioButton) findViewById(R.id.provide);
        provide.setVisibility(View.INVISIBLE);

        if(markerPoints.size() >= 2){
            LatLng origin = markerPoints.get(markerPoints.size()-2);
            LatLng dest = markerPoints.get(markerPoints.size()-1);

            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);
        }
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        //Logging
        //System.out.println("Get Directions URL is success.URL is"+url);
        //Toast.makeText(getApplicationContext(),"Get Directions URL is success"+url, Toast.LENGTH_SHORT).show();

        return url;

        //  showDirections(view, origin, dest);

    }
    /*public void showDirections(View view, LatLng origin, LatLng dest) {

        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr="+ origin.latitude + "," + origin.longitude + "&daddr=" + dest.latitude + "," +
                dest.longitude));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);

    }*/
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();
            //System.out.println("Download URL is success.URL is"+data);
            //Toast.makeText(getApplicationContext(),"Download URL is success"+data, Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Log.d("Exception while ","Exception while downloading URL" +e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }
            catch(Exception e)
            {
                Log.d("Exception" ,"Exception in in Background Async Task 1"+e.toString());
                // Toast.makeText(getApplicationContext(),
                //       "Exception in Background Async Task 1"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            //  Log.d("Exception" ,"post exceute result in in Background Async Task 1"+result);
            //Toast.makeText(getApplicationContext(),
            //  "Result of Download Async Task1"+result, Toast.LENGTH_SHORT).show();
            //System.out.println("hi");
            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >
    {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
                // Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            //Toast.makeText(getApplicationContext(),
            //      "Result of DownloadTask2"+result, Toast.LENGTH_SHORT).show();
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            // Toast.makeText(getBaseContext(), "Size is " + result.size(), Toast.LENGTH_SHORT).show();
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    //  Toast.makeText(getBaseContext(), "Lat is " + position.toString(), Toast.LENGTH_SHORT).show();
                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
            }
            //  Toast.makeText(getBaseContext(), "Near PolyLine", Toast.LENGTH_SHORT).show();

            // Drawing polyline in the Google Map for the i-th route
            // map.addPolyline(lineOptions);
            if (lineOptions != null)
            {
                mMap.addPolyline(lineOptions);
            }
                /*Toast.makeText(getApplicationContext(),
                        "Line added", Toast.LENGTH_SHORT).show();*/
        }
    }
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
}
