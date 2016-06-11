package com.example.durga.hackathon;

/**
 * Created by Durga on 4/29/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 Author: Rahul Sunder
 Created Date: 04th April 2016
 Last Modified Date: 29th May 2016
 Purpose: Activity class for Co-ordinating help offers and help requests for accommodation during disaster times.
*/
public class customAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    MainActivity mainActivity = new MainActivity();
    private static LayoutInflater inflater=null;
    public customAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                if (position == 0)
                {
                    //mainActivity.CheckEnableGPS();
                   // mainActivity.onCreate(Bundle savedInstanceState);

              //      mainActivity.checkNetworkStatus();
                    Intent myIntent = new Intent(context,
                            MapsActivity.class);
                    context.startActivity(myIntent);
                }
                if (position == 1)
                {
                    Intent myIntent = new Intent(context,
                            Accomodation.class);
                    context.startActivity(myIntent);
                }
                if (position == 2)
                {
                    Intent myIntent = new Intent(context,
                            Emergency.class);
                    context.startActivity(myIntent);
                }
                if (position == 3)
                {
                    Intent myIntent = new Intent(context,
                            FindFamily.class);
                    context.startActivity(myIntent);
                  //  mainActivity.CheckEnableGPS();
                }
            }
        });
        return rowView;
    }

}