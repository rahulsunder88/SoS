package com.example.durga.hackathon;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Locate your family
*/
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindFamily extends AppCompatActivity {
    String message = "getlocation";
    Button find;
    EditText phoneNumber;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // String phoneNumberReciver="9884574587";// phone number to which SMS to be send


        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        Button find_btn = (Button) findViewById(R.id.find);
        // Defining button click event listener for the find button
        View.OnClickListener findClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                number = phoneNumber.getText().toString();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number, null, message, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent to " + number ,
                        Toast.LENGTH_LONG).show();
            }
        };
        find_btn.setOnClickListener(findClickListener);

    }

}
