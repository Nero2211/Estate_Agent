package com.example.nero.estateagent.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nero.estateagent.Price;
import com.example.nero.estateagent.R;

import java.util.ArrayList;

/**
 * Created by Nero on 04/04/2017.
 */



public class CustomPriceAdapter extends ArrayAdapter<Price>{

    private ArrayList<Price> priceResult;
    private Context context;
    private int years;

    public CustomPriceAdapter(Context context, int resource, ArrayList<Price> result, int years){
        super(context, resource, result);

        this.context = context;
        this.priceResult = result;
        this.years = years;
        //this.price = price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.price_custom_listview, null);

        final Price price = priceResult.get(position);

        TextView areaLink = (TextView)rowView.findViewById(R.id.areaURL);

        TextView sevenYears = (TextView) rowView.findViewById(R.id.sevenYearsPrice);
        TextView titleSevenYear = (TextView) rowView.findViewById(R.id.listviewTitle7Years);

        TextView fiveYears = (TextView) rowView.findViewById(R.id.fiveYearsPrice);
        TextView titleFiveYear = (TextView) rowView.findViewById(R.id.listviewTitle5Years);

        TextView threeYears = (TextView) rowView.findViewById(R.id.threeYearsPrice);
        TextView titleThreeYear = (TextView) rowView.findViewById(R.id.listviewTitle3Years);

        TextView oneYear = (TextView) rowView.findViewById(R.id.oneYearPrice);
        TextView titleOneYear = (TextView) rowView.findViewById(R.id.listviewTitle1Year);

        TextView sevenYearQuantity = (TextView) rowView.findViewById(R.id.sevenYearsQuantity);
        TextView fiveYearsQuantity = (TextView) rowView.findViewById(R.id.fiveYearsQuantity);
        TextView threeYearsQuantity = (TextView) rowView.findViewById(R.id.threeYearsQuantity);
        TextView oneYearQuantity = (TextView) rowView.findViewById(R.id.oneYearQuantity);

        if(years == 7){
            fiveYears.setVisibility(View.GONE);
            fiveYearsQuantity.setVisibility(View.GONE);
            threeYears.setVisibility(View.GONE);
            threeYearsQuantity.setVisibility(View.GONE);
            oneYear.setVisibility(View.GONE);
            oneYearQuantity.setVisibility(View.GONE);

            titleFiveYear.setVisibility(View.GONE);
            titleOneYear.setVisibility(View.GONE);
            titleThreeYear.setVisibility(View.GONE);
        }

        else if(years == 5){
            sevenYears.setVisibility(View.GONE);
            sevenYearQuantity.setVisibility(View.GONE);
            threeYears.setVisibility(View.GONE);
            threeYearsQuantity.setVisibility(View.GONE);
            oneYear.setVisibility(View.GONE);
            oneYearQuantity.setVisibility(View.GONE);

            titleSevenYear.setVisibility(View.GONE);
            titleThreeYear.setVisibility(View.GONE);
            titleOneYear.setVisibility(View.GONE);
        }

        else if(years == 3){
            sevenYears.setVisibility(View.GONE);
            sevenYearQuantity.setVisibility(View.GONE);
            fiveYears.setVisibility(View.GONE);
            fiveYearsQuantity.setVisibility(View.GONE);
            oneYear.setVisibility(View.GONE);
            oneYearQuantity.setVisibility(View.GONE);

            titleSevenYear.setVisibility(View.GONE);
            titleFiveYear.setVisibility(View.GONE);
            titleOneYear.setVisibility(View.GONE);
        }

        else if(years == 1){
            sevenYears.setVisibility(View.GONE);
            sevenYearQuantity.setVisibility(View.GONE);
            fiveYears.setVisibility(View.GONE);
            fiveYearsQuantity.setVisibility(View.GONE);
            threeYears.setVisibility(View.GONE);
            threeYearsQuantity.setVisibility(View.GONE);

            titleSevenYear.setVisibility(View.GONE);
            titleThreeYear.setVisibility(View.GONE);
            titleFiveYear.setVisibility(View.GONE);
        }

        sevenYears.setText(String.valueOf(price.getPrice7Years()));
        fiveYears.setText(String.valueOf(price.getPrice5Years()));
        threeYears.setText(String.valueOf(price.getPrice3Years()));
        oneYear.setText(String.valueOf(price.getPrice1Year()));

        sevenYearQuantity.setText(String.valueOf(price.getQuantity7Years()));
        fiveYearsQuantity.setText(String.valueOf(price.getQuantity5Years()));
        threeYearsQuantity.setText(String.valueOf(price.getQuantity3Years()));
        oneYearQuantity.setText(String.valueOf(price.getQuantity1Year()));
        areaLink.setText(price.getAreaUrl());

        areaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("LAUNCH BROWSER");
                alert.setMessage("Are you sure you wish to open the link in browser?");
                //buttons for the dialog
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(price.getAreaUrl()));
                        context.startActivity(intent);
                    }
                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });


        return rowView;
    }

    public void setText(TextView myTextView, ArrayList<String> myArray, int position){
        myTextView.setText(myArray.get(position).toString());
    }
}
