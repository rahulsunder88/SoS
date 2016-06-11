package com.example.durga.hackathon;


/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Activity class for Co-ordinating help offers and help requests for Food during disaster times.
*/

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
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
import com.google.maps.android.SphericalUtil;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int noofLocations = 0;
    EditText phoneNumber;
    EditText name;
    RadioGroup type;
    RadioButton needprovideButton;
    // latitude and longitude
    //double latitude = ;
    //double longitude = ;
    private List<Marker> markerList;
    ArrayList<LatLng> markerPoints;
    public MapsActivity() {
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
        setContentView(R.layout.activity_maps);

        // Make edittext invisible

        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.INVISIBLE);

        type = (RadioGroup) findViewById(R.id.needprovide);
        type.setVisibility(View.INVISIBLE);

        // Options Start

        // Options End
        // Make edittext invisible end
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Getting Map for the SupportMapFragment
        mMap = mapFragment.getMap();
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
        View.OnClickListener findClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                mMap.clear();
                markerPoints.clear();
                name = (EditText) findViewById(R.id.name);
                name.setVisibility(View.INVISIBLE);

                phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                phoneNumber.setVisibility(View.INVISIBLE);

                type = (RadioGroup)findViewById(R.id.needprovide);
                type.setVisibility(View.INVISIBLE);
            }
        };
        // Setting button click event listener for the find button
        clear_Map.setOnClickListener(findClickListener1);
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

        // Clear Map
        Button clear_Map = (Button) findViewById(R.id.clear);
        View.OnClickListener findClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                googleMap.clear();
                markerPoints.clear();
                name = (EditText) findViewById(R.id.name);
                name.setVisibility(View.INVISIBLE);

                phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                phoneNumber.setVisibility(View.INVISIBLE);

                type = (RadioGroup)findViewById(R.id.needprovide);
                type.setVisibility(View.INVISIBLE);
            }
        };
        // Setting button click event listener for the find button
        clear_Map.setOnClickListener(findClickListener1);
        //getLocation(googleMap);
        //LoadPreferences();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng point) {
              //  saveLocation(point , googleMap);
                // getnearLocation(point, googleMap);
               // getDirections(point, googleMap);
                name = (EditText) findViewById(R.id.name);
                name.setVisibility(View.VISIBLE);

                phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                phoneNumber.setVisibility(View.VISIBLE);

                type = (RadioGroup)findViewById(R.id.needprovide);
                type.setVisibility(View.VISIBLE);

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
               // mMap.addMarker(new MarkerOptions().position(point).title("Added"));
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                // Clear Map
                Button clear_Map = (Button) findViewById(R.id.clear);
                View.OnClickListener findClickListener1 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start NewActivity.class
                        googleMap.clear();
                        markerPoints.clear();
                        name = (EditText) findViewById(R.id.name);
                        name.setVisibility(View.INVISIBLE);

                        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                        phoneNumber.setVisibility(View.INVISIBLE);

                        type = (RadioGroup)findViewById(R.id.needprovide);
                        type.setVisibility(View.INVISIBLE);
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
                        // Start NewActivity.class
                        saveLocation(saveAcc,googleMap);
                    }
                };


                // Setting button click event listener for the find button
                save_Accomodation.setOnClickListener(saveAcc);

                // End of Save Accomodation

                // Get Directions
                Button get_Directions = (Button) findViewById(R.id.getDirection);
                directionsAccOnClickListener getDir = new directionsAccOnClickListener( point)
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

                                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
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
                                      //  Toast.makeText(getApplicationContext(),"Transaction ID Created",1000).show();

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
                        //

                        return doNotMoveCameraToCenterMarker;
                    }
                });
    }



    private void getLocation( GoogleMap googleMap){
        int mylocation =0;
        String name;
        String phoneNumber;
        String type;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);


        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations", 0);
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
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);

                name = sharedPreferences.getString("name" + i, "0");
                phoneNumber = sharedPreferences.getString("phonenumber" + i, "0");
                type = sharedPreferences.getString("type" + i, "0");

              //   Toast.makeText(getBaseContext(), "the name is " + name, Toast.LENGTH_SHORT).show();
                // Drawing marker on the map
              //  MarkerOptions marker = new MarkerOptions().position(
                //        new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " "+ type);


                if (type.equals("Need Service" ))  {
                  //  googleMap.addMarker(marker);
                    googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }

                else
                {
              //  googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                //mMap.addMarker(options);
                }
            }

        }
        //  editor.commit();
    }
    private void saveLocation(LatLng point, GoogleMap googleMap){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("location", 0);
        noofLocations = sharedPreferences.getInt("noofLocations", 0);
        //
        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.VISIBLE);

        type = (RadioGroup) findViewById(R.id.needprovide);
        type.setVisibility(View.VISIBLE);

        // Toast.makeText(getBaseContext(), "no of locations is " + noofLocations, Toast.LENGTH_SHORT).show();
        noofLocations++ ;
        /** Opening the editor object to write data to sharedPreferences */
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Storing the latitude for the i-th location
        editor.putString("lat"+ Integer.toString((noofLocations)), Double.toString(point.latitude));

        // Storing the longitude for the i-th location
        editor.putString("lng"+ Integer.toString((noofLocations)), Double.toString(point.longitude));


        // Storing the name
        editor.putString("name"+ Integer.toString((noofLocations)), name.getText().toString());

        // Storing the Phone number
        editor.putString("phonenumber"+ Integer.toString((noofLocations)), phoneNumber.getText().toString());

        int selectedId = type.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        needprovideButton = (RadioButton) findViewById(selectedId);

        // Storing the type
        editor.putString("type"+ Integer.toString((noofLocations)), needprovideButton.getText().toString());


        // Storing the count of locations or marker count
        editor.putInt("noofLocations", noofLocations);

        /** Storing the zoom level to the shared preferences */
        editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));

        /** Saving the values stored in the shared preferences */
        editor.commit();
        String lat = sharedPreferences.getString("lat" + (noofLocations), "0");
         Toast.makeText(getBaseContext(), "Request Created", Toast.LENGTH_SHORT).show();
        // Toast.makeText(getBaseContext(), "The lat is " + noofLocations, Toast.LENGTH_SHORT).show();

        if (needprovideButton.getText().toString().equals("Need Service" )) {
           // googleMap.addMarker(marker);
            googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        else
        {
            //googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)));
            googleMap.addMarker(new MarkerOptions().position( new LatLng(point.latitude, point.longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            //mMap.addMarker(options);
        }

        name = (EditText) findViewById(R.id.name);
        name.setVisibility(View.INVISIBLE);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setVisibility(View.INVISIBLE);

        type = (RadioGroup) findViewById(R.id.needprovide);
        type.setVisibility(View.INVISIBLE);
    }
    private void getnearLocation( LatLng origin,GoogleMap googleMap, LatLng dest){
        int mylocation =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);


        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        googleMap.clear();
        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            String name;
            String phoneNumber;
            String type;
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
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);
                // Drawing marker on the map
                LatLng newPoint = new LatLng (latitude,longitude);
                // Get distance between origin and destination

                name = sharedPreferences.getString("name" + i, "0");
                phoneNumber = sharedPreferences.getString("phonenumber" + i, "0");
                type = sharedPreferences.getString("type" + i, "0");
                // Drawing marker on the map


                double distanceOriginDest;
                distanceOriginDest = SphericalUtil.computeDistanceBetween( origin, dest);
                double distanceOriginDestinkms;
                distanceOriginDestinkms = (distanceOriginDest / 1000);

                // Origin distance Calculation
                double distanceOrigin;
                distanceOrigin = SphericalUtil.computeDistanceBetween( origin, newPoint);
                double distanceOrigininKms;
                distanceOrigininKms = (distanceOrigin / 1000);

                // Destination Distance calculation
                double distanceDest;
                distanceDest = SphericalUtil.computeDistanceBetween( dest, newPoint);
                double distanceDestinKms;
                distanceDestinKms = (distanceDest / 1000);
                if (distanceOrigininKms <= distanceOriginDestinkms && distanceDestinKms <= distanceOriginDestinkms ) {
                    //  Toast.makeText(getBaseContext(), "The dis tance is " + String.valueOf(distanceinKms), Toast.LENGTH_SHORT).show();
                   // MarkerOptions marker = new MarkerOptions().position(
                    //        new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type);


                    if (type.equals("Need Service") )  {
                      //  googleMap.addMarker(marker);
                       googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }

                    else
                    {
                      //  googleMap.addMarker(marker);
                        googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)).title("Contact Details").snippet(name + " " + phoneNumber + " " + type).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        //mMap.addMarker(options);
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

        type = (RadioGroup) findViewById(R.id.needprovide);
        type.setVisibility(View.INVISIBLE);
        //markerPoints.add(point);

     //   MarkerOptions options = new MarkerOptions();


        //options.position(point);




        if(markerPoints.size() >= 2){
            LatLng origin = markerPoints.get(markerPoints.size()-2);
            LatLng dest = markerPoints.get(markerPoints.size()-1);
            getnearLocation(origin, googleMap, dest);
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

        return url;


        //  showDirections(view, origin, dest);

    }
    public void showDirections(View view, LatLng origin, LatLng dest) {

        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr="+ origin.latitude + "," + origin.longitude + "&daddr=" + dest.latitude + "," +
                dest.longitude));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);

    }
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

        }catch(Exception e){
            Log.d("Exception while ", e.toString());
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
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

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
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getKey( GoogleMap googleMap, double Mylatitude, double Mylongitude){
        int mylocation =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);

        //  Toast.makeText(getBaseContext(), "inside get key " , Toast.LENGTH_SHORT).show();
        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            String name;
            String type;
            String phoneNumber;
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
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);

                name = sharedPreferences.getString("name" + i, "0");
                phoneNumber = sharedPreferences.getString("phonenumber" + i, "0");
                type = sharedPreferences.getString("type" + i, "0");
                // Drawing marker on the map
             /*   MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("New Marker");

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));*/
                if (Mylatitude == latitude && Mylongitude == longitude)
                {
                    Toast.makeText(getBaseContext(), "Request Completed " , Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().remove("lat" + i).commit();
                    sharedPreferences.edit().remove("lng" + i).commit();
                    sharedPreferences.edit().remove("name" + i).commit();
                    sharedPreferences.edit().remove("type" + i).commit();
                    sharedPreferences.edit().remove("phonenumber" + i).commit();

                }
            }

        }
        //  editor.commit();
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
