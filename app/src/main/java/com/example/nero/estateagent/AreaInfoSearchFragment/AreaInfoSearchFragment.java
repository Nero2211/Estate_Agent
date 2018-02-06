package com.example.nero.estateagent.AreaInfoSearchFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.nero.estateagent.PriceSearchFragment.PriceResultFragment;
import com.example.nero.estateagent.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaInfoSearchFragment extends Fragment {

    EditText areaSearch;
    RadioButton edu, crime, ppl, tax, all;
    Boolean eduTick, crimeTick, pplTick, taxTick, allTick;
    String postCodeCheck;

    public AreaInfoSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_area_info_search, container, false);
        areaSearch = (EditText) v.findViewById(R.id.property_area_search_edittext);
        edu = (RadioButton)v.findViewById(R.id.education_rb);
        crime = (RadioButton)v.findViewById(R.id.crime_rb);
        ppl = (RadioButton)v.findViewById(R.id.people_rb);
        tax = (RadioButton)v.findViewById(R.id.counciltax_rb);
        all = (RadioButton)v.findViewById(R.id.allgraph_rb);

        postCodeCheck = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";

        allTick = true;
        taxTick = false;
        eduTick = false;
        pplTick = false;
        crimeTick = false;

        //ifRBischecked(all, allTick, eduTick, crimeTick, pplTick, taxTick);
        //ifRBischecked(edu, eduTick, allTick, crimeTick, pplTick, taxTick);
        //ifRBischecked(crime, crimeTick, eduTick, allTick, pplTick, taxTick);
        //ifRBischecked(ppl, pplTick, eduTick, crimeTick, allTick, taxTick);
        //ifRBischecked(tax, taxTick, eduTick, crimeTick, pplTick, allTick);

        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taxTick = true;
                eduTick = false;
                pplTick = false;
                crimeTick = false;
                allTick = false;
            }
        });


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTick = true;
                eduTick = false;
                taxTick = false;
                crimeTick = false;
                pplTick = false;
            }
        });

        edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eduTick = true;
                pplTick = false;
                taxTick = false;
                crimeTick = false;
                allTick = false;
            }
        });

        crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crimeTick = true;
                eduTick = false;
                taxTick = false;
                pplTick = false;
                allTick = false;
            }
        });

        ppl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pplTick = true;
                eduTick = false;
                taxTick = false;
                crimeTick = false;
                allTick = false;
            }
        });

        areaSearch.setOnKeyListener(new View.OnKeyListener() {
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

        return v;
    }

    public void moveNextFragment(){

        String search = areaSearch.getText().toString();
        Pattern p = Pattern.compile(postCodeCheck);
        Matcher m = p.matcher(search.toUpperCase());
        boolean b = m.matches();
        if(search.equals("")){
            Toast.makeText(getActivity(), "Please enter address",
                    Toast.LENGTH_LONG).show();
        }

        else if (b == true){
            String address = areaSearch.getText().toString();
            Fragment fr = new AreaInfoResultFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle args = new Bundle();
            args.putString("address", address);
            args.putBoolean("education", eduTick);
            args.putBoolean("tax", taxTick);
            args.putBoolean("people", pplTick);
            args.putBoolean("crime", crimeTick);
            args.putBoolean("all", allTick);
            fr.setArguments(args);
            ft.addToBackStack(null);
            ft.replace(R.id.fragment_container, fr);
            ft.commit();
        }
        else{
            Toast.makeText(getActivity(), "Please ensure you are using UK postcode and space where appropirate",
                    Toast.LENGTH_LONG).show();
        }
    }
}
