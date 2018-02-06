package com.example.nero.estateagent.PropertySearchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.com.nero.service.AbstractService;
import com.example.nero.estateagent.com.nero.service.PropertySearchService;
import com.example.nero.estateagent.com.nero.service.ServiceListener;
import com.example.nero.estateagent.com.nero.service.SpecificPropertyQueryService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;

import static com.example.nero.estateagent.R.array.propertyType;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyDescriptionFragment extends Fragment implements Serializable, OnMapReadyCallback, ServiceListener {

    String thumbnailURL, address, price, bedrooms, description, type, category, agentname, agentaddress,
            imageURL, latitude, longitude, listingid;
    TextView propertyAddress, propertyPrice, propertyBedrooms, propertyDescription, propertyAgentName,
            propertyAgentAddress, propertyCategory, propertyType, street;
    ImageView propertyImage;
    ArrayList<Property>saveMyProperty = new ArrayList<>();
    Property chosenProperty;
    GoogleMap propertyLocation;
    boolean propertyExist;
    boolean imageNull;
    private Thread thread;
    Property property;

    public PropertyDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_description, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

//        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
//        mapFragment.getMapAsync(this);

        listingid = getArguments().getString("listing");
        doSearch(listingid);

        propertyImage = (ImageView)v.findViewById(R.id.property_description_image);
        propertyAddress = (TextView)v.findViewById(R.id.property_description_address);
        propertyPrice = (TextView)v.findViewById(R.id.property_description_price);
        propertyBedrooms = (TextView)v.findViewById(R.id.property_description_bedrooms);
        propertyDescription = (TextView)v.findViewById(R.id.property_description_description);
        propertyAgentName = (TextView)v.findViewById(R.id.property_description_agentname);
        propertyAgentAddress = (TextView)v.findViewById(R.id.property_description_agentaddress);
        propertyType = (TextView)v.findViewById(R.id.property_description_type);
        propertyCategory = (TextView)v.findViewById(R.id.property_description_category);
        Button saveBtn = (Button)v.findViewById(R.id.property_description_save);
        ImageView goBack = (ImageView)v.findViewById(R.id.property_description_goBack);
        street = (TextView)v.findViewById(R.id.description_streetname);


//        if(imageURL.equals("null")){
//            propertyImage.setImageResource(R.drawable.no_image);
//            imageNull = true;
//        }
//        else{
//            Picasso.with(getContext()).load(imageURL).into(propertyImage);
//        }
        ScrollView parentScroll = (ScrollView)v.findViewById(R.id.property_description_parentScroll);
        ScrollView childScroll = (ScrollView)v.findViewById(R.id.property_description_childScroll);

        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.v("PARENT", "PARENT TOUCH");
                v.findViewById(R.id.property_description_childScroll).getParent()
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
//
//        propertyAddress.setText(address);
//        propertyPrice.setText("£"+ price);
//        propertyBedrooms.setText(bedrooms);
//        propertyDescription.setText(description);
//        propertyAgentName.setText(agentname);
//        propertyAgentAddress.setText(agentaddress);
//        propertyType.setText(type);
//        propertyCategory.setText(category);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                chosenProperty = new Property(null, property.getLargeImageURL(), property.getAddress(),
                        property.getType(), property.getAgent(), property.getPrice(), property.getBedrooms(),
                        property.getDescription(), property.getAgentAddress(), property.getCategory(), property.getLatitude(),
                        property.getLongitude(), property.getListingID(), property.getStreet(), imageNull);

                if(chosenProperty.getLargeImageURL().equals("null")){
                    chosenProperty.setImageNull(true);
                }

                try{
                    FileInputStream fis = getActivity().openFileInput("myfile");
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    saveMyProperty = (ArrayList) ois.readObject();


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                propertyExist = false;

                for(int i = 0; i < saveMyProperty.size(); i++){
                    if(saveMyProperty.get(i).getAddress().equals(chosenProperty.getAddress()) &&
                            saveMyProperty.get(i).getPrice().equals(chosenProperty.getPrice()) &&
                            saveMyProperty.get(i).getDescription().equals(chosenProperty.getDescription())){
                        Toast.makeText(getContext(), "Property is already saved in favourites", Toast.LENGTH_SHORT).show();
                        propertyExist = true;
                    }
                }
                if(propertyExist == false){
                    saveMyProperty.add(chosenProperty);
                    Toast.makeText(getContext(), "Property Saved Successfully!", Toast.LENGTH_SHORT).show();
                }
                try {
                    FileOutputStream fos = getContext().openFileOutput("myfile", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(saveMyProperty);
                    oos.close();
                    fos.close();

                } catch (IOException ioe) {
                    Log.v("MainActivity", ioe.toString());
                }

            }

        });

    }

    public String getStrPmetrs(String name){
        return getArguments().getString(name);
    }

    @Override
    public void serviceComplete(AbstractService abstractService) throws JSONException {
        if (!abstractService.hasError()) {
            SpecificPropertyQueryService specificPropertyQueryService = (SpecificPropertyQueryService) abstractService;
            try {
                property = new Property(null, specificPropertyQueryService.getResults().getJSONObject(0).getString("image_645_430_url"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("displayable_address"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("property_type"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("agent_name"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("price"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("num_bedrooms"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("description"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("agent_address"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("category"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("latitude"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("longitude"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("listing_id"),
                        specificPropertyQueryService.getResults().getJSONObject(0).getString("street_name"), imageNull);

                propertyAddress.setText(property.getAddress());
                propertyPrice.setText("£"+ getFormatedAmount(Integer.valueOf(property.getPrice())));
                propertyBedrooms.setText(property.getBedrooms());
                propertyDescription.setText(property.getDescription());
                propertyAgentName.setText(property.getAgent());
                propertyAgentAddress.setText(property.getAgentAddress());
                propertyType.setText(property.getType());
                propertyCategory.setText(property.getCategory());
                street.setText(property.getStreet());

                latitude = property.getLatitude();
                longitude = property.getLongitude();

                SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
                mapFragment.getMapAsync(this);

                if(property.getLargeImageURL().equals("null")){
                    propertyImage.setImageResource(R.drawable.no_image);
                }
                else{
                    Picasso.with(getContext()).load(property.getLargeImageURL()).into(propertyImage);
                }

            } catch (JSONException ex) {

            }
        }
    }

    public void doSearch(String query){
        String[] result = new String[]{query};

        SpecificPropertyQueryService specificPropertyQueryService = new SpecificPropertyQueryService(query);
        specificPropertyQueryService.addListener(this);
        thread = new Thread(specificPropertyQueryService);
        thread.start();
        System.out.println(result);
//        propertiesList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.property_custom_listview, R.id.property_listivew_propertyType, result));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        propertyLocation = googleMap;
        drawMarker(new LatLng(Double.parseDouble(property.getLatitude()), Double.parseDouble(property.getLongitude())));
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


    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.UK).format(amount);
    }
}
