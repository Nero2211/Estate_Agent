package com.example.nero.estateagent.CustomAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Nero on 13/03/2017.
 */

public class CustomPropertyAdapter extends ArrayAdapter<Property> {

    private ArrayList<Property> result;
    private Context context;

    public CustomPropertyAdapter(Context context, int resource, ArrayList<Property> property) {
        super(context, resource, property);

        this.context = context;
        this.result = property;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Property property = result.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.property_custom_listview, null);

        ImageView propertyImage = (ImageView) rowView.findViewById(R.id.property_thumbnail);
        TextView propertyType = (TextView) rowView.findViewById(R.id.property_listivew_propertyType);
        TextView propertyAddress = (TextView) rowView.findViewById(R.id.property_listivew_address);
        TextView propertyPrice = (TextView) rowView.findViewById(R.id.property_listivew_propertyPrice);
        TextView propertyDescription = (TextView) rowView.findViewById(R.id.property_listview_description);
        TextView numberOfBedrooms = (TextView) rowView.findViewById(R.id.property_listivew_bedroomNo);
        TextView agentName = (TextView) rowView.findViewById(R.id.property_listivew_agentName);

        if(property.isImageNull()== true){
            propertyImage.setImageResource(R.drawable.no_image);
        }else {
            Picasso.with(getContext()).load(property.getThumbnailURL()).into(propertyImage);
        }
        propertyType.setText(property.getType());
        propertyAddress.setText(property.getAddress());
        propertyPrice.setText(property.getPrice());
        propertyDescription.setText(property.getDescription());
        numberOfBedrooms.setText(property.getBedrooms());
        agentName.setText(property.getAgent());

        return rowView;
    }
}