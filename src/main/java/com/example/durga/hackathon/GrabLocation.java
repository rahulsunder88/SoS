package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Broadcast Receiver to listen for any queries from Master
*/
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class GrabLocation extends BroadcastReceiver {
    String phoneNumberReciver = "9884574587";

    @Override
    public void onReceive(Context context, Intent intent) {
        /* TODO Auto-generated method stub */

        ConnectivityManager connect =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo typMobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo typWifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // Check for network connections
        if ((typMobile != null && typMobile.isConnectedOrConnecting()) || (typWifi != null && typWifi.isConnectedOrConnecting())) {

            // if connected with internet


           // Toast.makeText(context, "internet is on", Toast.LENGTH_SHORT).show();
            int n = 0;
            Gps_Tracker gps;
            gps = new Gps_Tracker(context);
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return;
            }
            SmsMessage smsMessage[] = new SmsMessage[pdus.length];
            for (n = 0; n < pdus.length; n++) {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) pdus[n]);
            }
            // show first message
            String sms1 = smsMessage[0].getMessageBody();
            //                     	String from = smsMessage[0].getOriginatingAddress();
           // Toast.makeText(context, sms1, Toast.LENGTH_LONG).show();
            //              	       String name=getPhonenumber(from);
            if (sms1.contains("Getlocation") || sms1.contains("GETLOCATION") || sms1.contains("getlocation")) {

                //                	 Gps_Tracker gps = null;
                //          		 gps = new Gps_Tracker(context);
                //        	 Intent intent1 = new Intent(context, StartTracking.class);
                //           context.startActivity(intent1);
                if (gps != null) {

                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        try {
                            String address;
                            address = getAddress(context, latitude, longitude);
                        //    Toast.makeText(context, "donno 2" + address, Toast.LENGTH_LONG).show();
                            //		Toast.makeText(context, "Your Location is " + address, Toast.LENGTH_LONG).show();
                            // phone number to which SMS to be send
                            String message = "location is" + address;// message to send
                            SmsManager sms = SmsManager.getDefault();
                          //  Toast.makeText(context, "Reading Message", Toast.LENGTH_LONG).show();
                            sms.sendTextMessage(phoneNumberReciver, null, message, null, null);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                          //  Toast.makeText(context, "some error", Toast.LENGTH_LONG).show();
                            LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                            //String providerName=LocationManager.PASSIVE_PROVIDER;  was using this earlier but now changed to network provider
                            String providerName = LocationManager.NETWORK_PROVIDER;
                            Location location = locMgr.getLastKnownLocation(providerName);
                            updateWithLocation(location);
                        }

                        // \n is for new line

                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        // gps.showSettingsAlert();

                    }

                }

            }
        } else {

            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return;
            }
            int m = 0;
            SmsMessage smsMessage[] = new SmsMessage[pdus.length];
            for (m = 0; m < pdus.length; m++) {
                smsMessage[m] = SmsMessage.createFromPdu((byte[]) pdus[m]);
            }
            // show first message
            String sms2 = smsMessage[0].getMessageBody();
            //                     	String from = smsMessage[0].getOriginatingAddress();
            //Toast.makeText(context, sms2, Toast.LENGTH_LONG).show();
            //              	       String name=getPhonenumber(from);
            if (sms2.contains("Getlocation") || sms2.contains("GETLOCATION") || sms2.contains("getlocation")) {
               // Toast.makeText(context, "internet is off", Toast.LENGTH_SHORT).show();

                LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //String providerName=LocationManager.PASSIVE_PROVIDER;  was using this earlier but now changed to network provider
                String providerName = LocationManager.NETWORK_PROVIDER;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locMgr.getLastKnownLocation(providerName);
                updateWithLocation(location);
            }

        }
        //  MainActivity.speakSMS(sms1);

    }

    public String getAddress(Context context,double lat, double lng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        addresses = geocoder.getFromLocation(lat, lng, 1);
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getAddressLine(1);
        String country = addresses.get(0).getAddressLine(2);
        return address + "," + city + "," + country;
    }

    private void updateWithLocation(Location location) {

        String displayString;
        //   TextView locText=(TextView)findViewById(R.id.textView3);
        if(location!=null){
            Double lat=location.getLatitude();
            Double lng=location.getLongitude();
            Long calTime=location.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calTime);

            String time=formatter.format(calendar.getTime()).toString();
            displayString="The Lat: "+lat.toString()+" \nAnd Long: "
                    +lng.toString()
                    +time;

            String TowerLocation;
            //   	TowerLocation = getAddress(lat, lng);
            //   	locText.setText(TowerLocation);

            // phone number to which SMS to be send
            String message="location is" + displayString;// message to send
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumberReciver, null, message, null, null);



        }else{
            displayString="No details Found";
        }
        //  locText.setText(displayString);
    }



}
