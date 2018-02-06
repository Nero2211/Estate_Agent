package com.example.nero.estateagent.FavouritePropertyFragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nero.estateagent.AreaInfoSearchFragment.AreaInfoSearchFragment;
import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.interfaces.CustomPropertyInteractor;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.R.attr.value;
import static java.lang.System.getProperties;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteDescriptionFragment extends Fragment implements OnMapReadyCallback{

    String thumbnailURL, address, price, bedrooms, description, type, category, agentname, agentaddress,
            imageURL, latitude, longitude, street;
    int position;
    private Thread imageThread;
    ImageView propertyImage;
    ArrayList<Property> savedProperties = new ArrayList<>();
    GoogleMap propertyLocation;

    public FavouriteDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourite_description, container, false);

        position = getArguments().getInt("position");
        address = getArguments().getString("address");
        thumbnailURL = getStrPmetrs("thumbnailURL");
        imageURL = getStrPmetrs("URL");
        price = getStrPmetrs("price");
        bedrooms = getStrPmetrs("bedrooms");
        description = getStrPmetrs("description");
        type = getStrPmetrs("type");
        category = getStrPmetrs("category");
        agentname = getStrPmetrs("agentname");
        agentaddress = getStrPmetrs("agentaddress");
        latitude = getStrPmetrs("latitude");
        longitude = getStrPmetrs("longitude");
        street = getStrPmetrs("street");

        propertyImage = (ImageView)v.findViewById(R.id.favourites_description_image);
        TextView propertyAddress = (TextView)v.findViewById(R.id.favourite_description_address);
        TextView propertyPrice = (TextView)v.findViewById(R.id.favourite_description_price);
        TextView propertyBedrooms = (TextView)v.findViewById(R.id.favourite_description_bedrooms);
        TextView propertyDescription = (TextView)v.findViewById(R.id.favourite_description_description);
        TextView propertyAgentName = (TextView)v.findViewById(R.id.favourite_description_agentname);
        TextView propertyAgentAddress = (TextView)v.findViewById(R.id.favourite_description_agentaddress);
        TextView propertyType = (TextView)v.findViewById(R.id.favourite_description_type);
        TextView propertyCategory = (TextView)v.findViewById(R.id.favourite_description_category);
        Button removebtn = (Button)v.findViewById(R.id.favourite_description_remove);
        ImageView goBack = (ImageView)v.findViewById(R.id.favourite_goBack);
        TextView streetName = (TextView)v.findViewById(R.id.fav_description_streetname);

        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.favouritemap));
        mapFragment.getMapAsync(this);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
//                Fragment fr = new FavouriteListFragment();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_container, fr);
//                ft.commit();
            }
        });

        ScrollView parentScroll = (ScrollView)v.findViewById(R.id.parentScroll);
        ScrollView childScroll = (ScrollView)v.findViewById(R.id.childScroll);

        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.v("PARENT", "PARENT TOUCH");
                v.findViewById(R.id.childScroll).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.v("CHILD", "CHILD TOUCH");
                // Disallow the touch request for parent scroll on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        propertyAddress.setText(address);
        propertyPrice.setText("£"+ price);
        propertyBedrooms.setText(bedrooms);
        propertyDescription.setText(description);
        streetName.setText(street);
        if(imageURL.equals("null")){
            propertyImage.setImageResource(R.drawable.no_image);
        }
        else{
            Picasso.with(getContext()).load(imageURL).into(propertyImage);
        }
        propertyAgentName.setText(agentname);
        propertyAgentAddress.setText(agentaddress);
        propertyType.setText(type);
        propertyCategory.setText(category);

        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("DELETE");
                alert.setMessage("Are you sure you wish to delete the property from your favourite list?");
                //buttons for the dialog
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedProperty(position);
                        getFragmentManager().popBackStackImmediate();
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

        return v;
    }

    public String getStrPmetrs(String name){
        return getArguments().getString(name);
    }

    public int getIntPmeters(String name){
        return getArguments().getInt(name);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        propertyLocation = googleMap;
        drawMarker(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        propertyLocation.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        propertyLocation.animateCamera(zoom);

    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        propertyLocation.addMarker(markerOptions);
    }

    public void deleteSelectedProperty(int position){
        try{
            FileInputStream fis = getContext().openFileInput("myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);

            savedProperties = (ArrayList) ois.readObject();

        }catch(IOException ioe){
            Log.v("MainActivity", ioe.toString());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //deleting the record
        savedProperties.remove(position);
        //saving the array list
        try{
            FileOutputStream fos = getContext().openFileOutput("myfile", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(savedProperties);
            oos.close();
            fos.close();
            Toast.makeText(getContext(), "Property deleted Successfully!", Toast.LENGTH_SHORT).show();
        }catch(IOException ioe){
            Log.v("MainActivity", ioe.toString());
        }
    }

}
