package com.example.nero.estateagent.PropertySearchFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nero.estateagent.CustomAdapter.CustomPropertyRecyclerAdapter;
import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.CustomAdapter.CustomPropertyAdapter;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.com.nero.service.AbstractService;
import com.example.nero.estateagent.com.nero.service.PropertySearchService;
import com.example.nero.estateagent.com.nero.service.ServiceListener;
import com.example.nero.estateagent.interfaces.CustomPropertyInteractor;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyListFragment extends Fragment implements ServiceListener, CustomPropertyInteractor {

    String address;
    RecyclerView propertiesList;
    private ArrayList<Property> mySearch;
    private Thread thread;
    String minBedroomsString, maxBedroomsString, minPriceString, maxPriceString, propertyTypeString;
    TextView resultTV;

    public PropertyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        address = getArguments().getString("address");
        minPriceString = getArguments().getString("minPrice");
        maxPriceString = getArguments().getString("maxPrice");
        minBedroomsString = getArguments().getString("minimumBedrooms");
        maxBedroomsString = getArguments().getString("maximumBedrooms");
        propertyTypeString = getArguments().getString("propertyType");

        setValueformaxBed();
        setValueformaxPrice();
        setValueforminBed();
        setValueforminPrice();

        View v = inflater.inflate(R.layout.fragment_property_list, container, false);
        propertiesList = (RecyclerView) v.findViewById(R.id.property_list_listview);
        propertiesList.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView goBack = (ImageView)v.findViewById(R.id.property_list_goBack);
        resultTV = (TextView)v.findViewById(R.id.property_list_textView);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        mySearch = new ArrayList<>();
        doSearch(address, minBedroomsString, maxBedroomsString,
                minPriceString, maxPriceString);

//        propertiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Fragment fr = new PropertyDescriptionFragment();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Bundle args = new Bundle();
//                args.putString("address", mySearch.get(position).getAddress());
//                args.putString("URL", mySearch.get(position).getLargeImageURL());
//                args.putString("thumbnailURL", mySearch.get(position).getThumbnailURL());
//                args.putString("price", mySearch.get(position).getPrice());
//                args.putString("bedrooms", mySearch.get(position).getBedrooms());
//                args.putString("description", mySearch.get(position).getDescription());
//                args.putString("type", mySearch.get(position).getType());
//                args.putString("category", mySearch.get(position).getCategory());
//                args.putString("agentname", mySearch.get(position).getAgent());
//                args.putString("agentaddress", mySearch.get(position).getAgentAddress());
//                args.putString("latitude", mySearch.get(position).getLatitude());
//                args.putString("longitude", mySearch.get(position).getLongitude());
//                fr.setArguments(args);
//                ft.replace(R.id.fragment_container, fr);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });

        return v;
    }

    @Override
    public void serviceComplete(AbstractService abstractService) {
        if (!abstractService.hasError()) {
            PropertySearchService propertySearchService = (PropertySearchService) abstractService;

                for (int i = 0; i < propertySearchService.getResults().length(); i++) {
                try {

                    boolean imageNull = false;
                    if(propertySearchService.getResults().getJSONObject(i).getString("image_150_113_url").equals("null")){
                        imageNull = true;
                    }

                    Property property = new Property(propertySearchService.getResults().getJSONObject(i).getString("image_150_113_url"),
                            propertySearchService.getResults().getJSONObject(i).getString("image_645_430_url"),
                            propertySearchService.getResults().getJSONObject(i).getString("displayable_address"),
                            propertySearchService.getResults().getJSONObject(i).getString("property_type"),
                            null,
                            propertySearchService.getResults().getJSONObject(i).getString("price"),
                            propertySearchService.getResults().getJSONObject(i).getString("num_bedrooms"),
                            null,
                            null,
                            null,
                            null, null, propertySearchService.getResults().getJSONObject(i).getString("listing_id"), null, imageNull);

                        if(propertyTypeString.equals("All")){
                            mySearch.add(property);
                        }
                        else if(propertyTypeString.equals(property.getType())){
                            mySearch.add(property);
                        }
                } catch (JSONException ex) {

                }
            }
//                ArrayAdapter<Property> adapter = new CustomPropertyAdapter(getActivity(), 0, mySearch);
            CustomPropertyRecyclerAdapter adapter = new CustomPropertyRecyclerAdapter(getContext(), mySearch);
            adapter.setInteractor(this);
            propertiesList.setAdapter(adapter);
        }
        if(mySearch.isEmpty()) {
            resultTV.setText("No Properties Found, Please Try Again");
            resultTV.setVisibility(View.VISIBLE);
            propertiesList.setVisibility(View.GONE);
        }

        else {
            resultTV.setVisibility(View.GONE);
        }
    }

    public void doSearch(String address, String minimumBedrooms, String maximumBedrooms,
                         String minimumPrice, String maximumPrice){
        String[] result = new String[]{address};

        PropertySearchService propertySearchService = new PropertySearchService(address, minimumBedrooms, maximumBedrooms
        , minimumPrice, maximumPrice);
        propertySearchService.addListener(this);
        thread = new Thread(propertySearchService);
        thread.start();
        System.out.println(result);
//        propertiesList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.property_custom_listview, R.id.property_listivew_propertyType, result));
    }


    @Override
    public void OpenPropertyDescription(Property property) {
        Fragment fr = new PropertyDescriptionFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();
        args.putString("listing", property.getListingID());
        fr.setArguments(args);
        ft.replace(R.id.fragment_container, fr);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onLongClick(Property property) {

    }

    public void setValueformaxBed(){
        if(maxBedroomsString.equals("No Max")){
            maxBedroomsString = "100";
        }
    }
    public void setValueformaxPrice(){
        if(maxPriceString.equals("No Max")){
            maxPriceString = "10000000000";
        }
    }
    public void setValueforminBed(){
        if(minBedroomsString.equals("No Min")){
            minBedroomsString = "0";
        }
    }
    public void setValueforminPrice(){
        if(minPriceString.equals("No Min")){
            minPriceString = "0";
        }
    }

}
