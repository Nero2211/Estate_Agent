package com.example.nero.estateagent.PriceSearchFragment;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nero.estateagent.CustomAdapter.CustomPriceAdapter;
import com.example.nero.estateagent.Price;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.com.nero.service.AbstractService;
import com.example.nero.estateagent.com.nero.service.PriceSearchService;
import com.example.nero.estateagent.com.nero.service.PropertySearchService;
import com.example.nero.estateagent.com.nero.service.ServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class PriceResultFragment extends Fragment implements ServiceListener {

    String address;
    ListView priceList;
    private Thread thread;
    private ArrayList<Price> priceResult;
    ImageView goBack;
    Boolean sevenTick, fiveTick, threeTick, oneTick, allTick;
    TextView noResults;


    public PriceResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_price_result, container, false);
        address = getArguments().getString("address");
        sevenTick = getArguments().getBoolean("sevenYears");
        fiveTick = getArguments().getBoolean("fiveYears");
        threeTick = getArguments().getBoolean("threeYears");
        oneTick = getArguments().getBoolean("oneYear");
        allTick = getArguments().getBoolean("all");

        priceList = (ListView)v.findViewById(R.id.property_price_listview);
        goBack = (ImageView)v.findViewById(R.id.property_price_goBack);
        noResults = (TextView)v.findViewById(R.id.price_info_title);

        priceResult = new ArrayList<>();
        doSearch(address);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        return v;
    }

    @Override
    public void serviceComplete(AbstractService abstractService) {
        if(!abstractService.hasError()) {
            PriceSearchService priceSearchService = (PriceSearchService) abstractService;

            for (int i = 0; i < priceSearchService.getResults().length(); i++) {
                try {
                    Price price = new Price(priceSearchService.getResults().getJSONObject(i).getString("average_sold_price_7year"),
                            priceSearchService.getResults().getJSONObject(i).getString("average_sold_price_5year"),
                            priceSearchService.getResults().getJSONObject(i).getString("average_sold_price_3year"),
                            priceSearchService.getResults().getJSONObject(i).getString("average_sold_price_3year"),
                            priceSearchService.getResults().getJSONObject(i).getString("number_of_sales_7year"),
                            priceSearchService.getResults().getJSONObject(i).getString("number_of_sales_5year"),
                            priceSearchService.getResults().getJSONObject(i).getString("number_of_sales_3year"),
                            priceSearchService.getResults().getJSONObject(i).getString("number_of_sales_1year"),
                            priceSearchService.getResults().getJSONObject(i).getString("prices_url"));
                    priceResult.add(price);
                } catch (JSONException ex) {

                }
            }

            int years = 0;
            if(sevenTick){
                years = 7;
            }
            else if(fiveTick){
                years = 5;
            }
            else if(threeTick){
                years = 3;
            }
            else if(oneTick){
                years = 1;
            }
            noResults.setVisibility(View.GONE);
            priceList.setVisibility(View.VISIBLE);
            ArrayAdapter<Price> adapter = new CustomPriceAdapter(getActivity(), 0, priceResult, years);
            priceList.setAdapter(adapter);
        }
        else{
            noResults.setText("No Results Found, Please Try Again");
            noResults.setVisibility(View.VISIBLE);
            priceList.setVisibility(View.GONE);
//            String[] result = new String[]{"No results found"};
//            priceList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.price_custom_listview,
//                    R.id.listviewCounty, result));
        }
    }

    public void doSearch(String query){
        String[] result = new String[]{query};

        PriceSearchService priceSearchService = new PriceSearchService(query);
        priceSearchService.addListener(this);
        thread = new Thread(priceSearchService);
        thread.start();
    }

    public void goBack(){
        getFragmentManager().popBackStackImmediate();
    }

}