package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Main Activity class for SoS Application.
*/
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int [] prgmImages={R.mipmap.food,R.mipmap.accomodation,R.mipmap.emergency,R.mipmap.family};
    public static String [] prgmNameList={"Give / Pick up Food","Accomodation","Emergency","Find My Family"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // System.out.println("hi1 ..............................................****************");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        lv=(ListView) findViewById(R.id.listView);

        lv.setAdapter(new customAdapter(this, prgmNameList,prgmImages));
        //  System.out.println("hi2 ..............................................****************");
        CheckEnableGPS();
        checkNetworkStatus();
        // System.out.println("hi33 ..............................................****************");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        //System.out.println("hi4 ..............................................****************");

        return true;

    }

    public void CheckEnableGPS(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.equals("")){
            //GPS Enabled
            Toast.makeText(MainActivity.this, "GPS is Enabled: " + provider,
                    Toast.LENGTH_LONG).show();
            System.out.println("hi5 ..............................................****************");
        }else{


            System.out.println("hi6 ..............................................****************");

            System.out.println("hi7 ..............................................****************");

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("GPS is switched off, SOS app needs GPS enabled");
            //alertDialog.setMessage(caller);
            alertDialog.setButton2("NO",new DialogInterface.OnClickListener()
            {
                @SuppressLint("ShowToast")
                public void onClick(final DialogInterface dialog,final int which)
                {



                    finish();
                    Toast.makeText(getApplicationContext(),"Thank you",1000).show();
                }
            });
            alertDialog.setButton3("YES",new DialogInterface.OnClickListener()
            {

                @SuppressLint("ShowToast")
                public void onClick(final DialogInterface dialog,final int which)
                {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.show();
        }

    }

    public void  checkNetworkStatus(){

        final ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isConnected() ){

            Toast.makeText(this, "Wifi" , Toast.LENGTH_LONG).show();
        }
        else if( mobile.isConnected() ){

            Toast.makeText(this, "Mobile 3G " , Toast.LENGTH_LONG).show();
        }
        else
        {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("Internet is switched off, SOS app needs Internet enabled");
            //alertDialog.setMessage(caller);
            alertDialog.setButton2("NO",new DialogInterface.OnClickListener()
            {
                @SuppressLint("ShowToast")
                public void onClick(final DialogInterface dialog,final int which)
                {


                    finish();

                    Toast.makeText(getApplicationContext(),"Thank you",1000).show();
                }

            });
            alertDialog.setButton3("YES",new DialogInterface.OnClickListener()
            {

                @SuppressLint("ShowToast")
                public void onClick(final DialogInterface dialog,final int which)
                {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    //startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    startActivity(intent);
                    if (! wifi.isConnected() | !mobile.isConnected())
                    {
                        return;
                    }
                }
            });
            alertDialog.show();
        }

    }

}