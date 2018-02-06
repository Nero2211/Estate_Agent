package com.example.nero.estateagent.PropertySearchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nero.estateagent.AreaInfoSearchFragment.AreaInfoResultFragment;
import com.example.nero.estateagent.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertySearchFragment extends Fragment {

    EditText searchET;
    Spinner minPrice, maxPrice, minBedroom, maxBedroom, propType;
    String minBedroomCount, maxBedroomCount, propertyType, minPriceString, maxPriceString;

    public PropertySearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_property_search, container, false);

        searchET = (EditText)v.findViewById(R.id.property_search_edittext);
        minPrice = (Spinner)v.findViewById(R.id.property_search_spn_nominPrice);
        maxPrice = (Spinner)v.findViewById(R.id.property_search_spn_nomaxPrice);
        minBedroom = (Spinner)v.findViewById(R.id.property_search_spn_nominBedroom);
        maxBedroom = (Spinner)v.findViewById(R.id.property_search_spn_nomaxBedroom);
        propType = (Spinner)v.findViewById(R.id.property_search_spn_propertyType);

        searchET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == android.view.KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case android.view.KeyEvent.KEYCODE_DPAD_CENTER:
                        case android.view.KeyEvent.KEYCODE_ENTER:
                            moveNextFragment();
                            InputMethodManager inputManager =
                                    (InputMethodManager) getContext().
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> minPriceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.minimumPrice, R.layout.spinner_item);
        minPriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minPrice.setAdapter(minPriceAdapter);

        ArrayAdapter<CharSequence> maxPriceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.maximumPrice, R.layout.spinner_item);
        maxPriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxPrice.setAdapter(maxPriceAdapter);

        ArrayAdapter<CharSequence> minBedroomAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.minimumBedrooms, R.layout.spinner_item);
        minBedroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minBedroom.setAdapter(minBedroomAdapter);

        ArrayAdapter<CharSequence> maxBedroomAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.maximumBedrooms, R.layout.spinner_item);
        maxBedroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxBedroom.setAdapter(maxBedroomAdapter);

        ArrayAdapter<CharSequence> propertyTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.propertyType, R.layout.spinner_item);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propType.setAdapter(propertyTypeAdapter);

        return v;
    }

    public void moveNextFragment(){
        minPriceString = minPrice.getSelectedItem().toString();
        maxPriceString = maxPrice.getSelectedItem().toString();
        minBedroomCount = minBedroom.getSelectedItem().toString();
        maxBedroomCount = maxBedroom.getSelectedItem().toString();
        propertyType = propType.getSelectedItem().toString();

        String search = searchET.getText().toString();
        if(search.equals("")){
            Toast.makeText(getActivity(), "Please enter address",
                    Toast.LENGTH_LONG).show();
        }else {
            String address = searchET.getText().toString();
            Fragment fr = new PropertyListFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle args = new Bundle();
            args.putString("address", address);
            args.putString("minPrice", minPriceString);
            args.putString("maxPrice", maxPriceString);
            args.putString("minimumBedrooms", minBedroomCount);
            args.putString("maximumBedrooms", maxBedroomCount);
            args.putString("propertyType", propertyType);

            fr.setArguments(args);
            ft.replace(R.id.fragment_container, fr, "ListFragment");
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
