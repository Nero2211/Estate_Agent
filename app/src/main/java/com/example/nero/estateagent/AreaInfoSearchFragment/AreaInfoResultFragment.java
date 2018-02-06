package com.example.nero.estateagent.AreaInfoSearchFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nero.estateagent.R;
import com.example.nero.estateagent.com.nero.service.AbstractService;
import com.example.nero.estateagent.com.nero.service.AreaInfoSearchService;
import com.example.nero.estateagent.com.nero.service.PropertySearchService;
import com.example.nero.estateagent.com.nero.service.ServiceListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaInfoResultFragment extends Fragment implements ServiceListener {

    String address;
    String edu;
    ImageView educationGraph, taxGraph, crimeGraph, pplGraph;
    TextView street, town, county, country, postcode, edutv, crimetv, ppltv, taxtv;
    TextView noResults;
    LinearLayout linearLayout;
    Boolean eduTick, crimeTick, pplTick, taxTick, allTick;
    private Thread thread;

    public AreaInfoResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_area_info_result, container, false);

        address = getArguments().getString("address");
        eduTick = getArguments().getBoolean("education");
        crimeTick = getArguments().getBoolean("crime");
        pplTick = getArguments().getBoolean("people");
        taxTick = getArguments().getBoolean("tax");
        allTick = getArguments().getBoolean("all");


        edutv = (TextView)v.findViewById(R.id.education_tv);
        crimetv = (TextView)v.findViewById(R.id.crime_tv);
        taxtv = (TextView)v.findViewById(R.id.tax_tv);
        ppltv = (TextView)v.findViewById(R.id.ppl_tv);
        ImageView goBack = (ImageView)v.findViewById(R.id.property_area_search_goBack);
        noResults = (TextView)v.findViewById(R.id.area_info_title);
        linearLayout = (LinearLayout)v.findViewById(R.id.area_info_layout);



        street = (TextView)v.findViewById(R.id.area_search_street);
        town = (TextView)v.findViewById(R.id.area_search_town);
        county = (TextView)v.findViewById(R.id.area_search_county);
        country = (TextView)v.findViewById(R.id.area_search_country);
        postcode = (TextView)v.findViewById(R.id.area_search_postcode);
        educationGraph = (ImageView)v.findViewById(R.id.area_search_educationGraph);
        taxGraph = (ImageView)v.findViewById(R.id.area_search_taxGraph);
        crimeGraph = (ImageView)v.findViewById(R.id.area_search_crimeGraph);
        pplGraph = (ImageView)v.findViewById(R.id.area_search_peopleGraph);


        doSearch(address);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        return v;
    }

    @Override
    public void serviceComplete(AbstractService abstractService) throws JSONException {
        if(!abstractService.hasError()) {
            AreaInfoSearchService areaInfoSearchService = (AreaInfoSearchService) abstractService;

            edu = areaInfoSearchService.getResults().get(0);
            String crime = areaInfoSearchService.getResults().get(1);
            String tax = areaInfoSearchService.getResults().get(2);
            String ppl = areaInfoSearchService.getResults().get(3);
            String stString = areaInfoSearchService.getResults().get(4);
            String townString = areaInfoSearchService.getResults().get(5);
            String postcodeString = areaInfoSearchService.getResults().get(6);
            String countyString = areaInfoSearchService.getResults().get(7);
            String countryString = areaInfoSearchService.getResults().get(8);

            street.setText(stString);
            town.setText(townString);
            postcode.setText(postcodeString);
            county.setText(countyString);
            country.setText(countryString);

            educationGraph.setScaleType(ImageView.ScaleType.FIT_XY);
            taxGraph.setScaleType(ImageView.ScaleType.FIT_XY);
            crimeGraph.setScaleType(ImageView.ScaleType.FIT_XY);
            pplGraph.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(getContext()).load(edu).into(educationGraph);
            Picasso.with(getContext()).load(crime).into(crimeGraph);
            Picasso.with(getContext()).load(tax).into(taxGraph);
            Picasso.with(getContext()).load(ppl).into(pplGraph);

            if(allTick == true) {

            }
            else if(eduTick == true){
                taxtv.setVisibility(View.GONE);
                ppltv.setVisibility(View.GONE);
                crimetv.setVisibility(View.GONE);

                taxGraph.setVisibility(View.GONE);
                crimeGraph.setVisibility(View.GONE);
                pplGraph.setVisibility(View.GONE);
            }
            else if(crimeTick == true){
                taxtv.setVisibility(View.GONE);
                ppltv.setVisibility(View.GONE);
                edutv.setVisibility(View.GONE);

                educationGraph.setVisibility(View.GONE);
                taxGraph.setVisibility(View.GONE);
                pplGraph.setVisibility(View.GONE);

            }
            else if(pplTick == true){
                taxtv.setVisibility(View.GONE);
                edutv.setVisibility(View.GONE);
                crimetv.setVisibility(View.GONE);

                educationGraph.setVisibility(View.GONE);
                crimeGraph.setVisibility(View.GONE);
                taxGraph.setVisibility(View.GONE);

            }
            else if(taxTick == true){
                edutv.setVisibility(View.GONE);
                ppltv.setVisibility(View.GONE);
                crimetv.setVisibility(View.GONE);

                educationGraph.setVisibility(View.GONE);
                crimeGraph.setVisibility(View.GONE);
                pplGraph.setVisibility(View.GONE);

            }
            }

        if(edu == null){
            linearLayout.setVisibility(View.GONE);
            noResults.setText("No Results Found, Please Try Again");
            noResults.setVisibility(View.VISIBLE);
        }else {
            noResults.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


    public void doSearch(String query){
        AreaInfoSearchService areaInfoSearchService = new AreaInfoSearchService(query);
        areaInfoSearchService.addListener(this);
        thread = new Thread(areaInfoSearchService);
        thread.start();

    }
}
