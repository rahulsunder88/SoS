package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Activity class to send back location as SMS.
*/
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {
    String Address ="else";
    String phoneNum ="9841977645";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            Address = bundle.getString("address");
            Toast.makeText(getApplicationContext(), "Your Location is " + Address.toString(), Toast.LENGTH_LONG).show();
            // phoneNum = bundle.getString("phoneNum");
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("SoS Message");
        alertDialog.setMessage("The location of your Requested Member:" + Address);
        alertDialog.setButton("OK",new DialogInterface.OnClickListener()
        {
            @SuppressLint({ "ShowToast", "NewApi" })
            public void onClick(final DialogInterface dialog,final int which)
            {

                Toast.makeText(getApplicationContext(),"Thank you!",1000).show();

            }
        });


        alertDialog.show();
    }

}
