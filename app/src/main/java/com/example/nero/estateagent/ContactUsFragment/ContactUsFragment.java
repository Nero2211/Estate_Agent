package com.example.nero.estateagent.ContactUsFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nero.estateagent.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements OnMapReadyCallback {


    GoogleMap propertyLocation;
    String latitude = "52.588658";
    String longitude = "-1.982422";

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);

        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.contactUSmap));
        mapFragment.getMapAsync(this);

        return v;
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

}
