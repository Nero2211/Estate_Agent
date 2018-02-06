package com.example.nero.estateagent.PriceSearchFragment;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.nero.estateagent.PropertySearchFragments.PropertyListFragment;
import com.example.nero.estateagent.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceSearchFragment extends Fragment {


    EditText searchET;
    String postCodeCheck;
    RadioButton sevenY, fiveY, threeY, oneY, all;
    Boolean sevenTick, fiveTick, threeTick, oneTick, allTick;

    public PriceSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_price_search, container, false);
        searchET = (EditText)v.findViewById(R.id.property_price_search_edittext);
        sevenY = (RadioButton)v.findViewById(R.id.sevenYearP_rb);
        fiveY = (RadioButton)v.findViewById(R.id.fiveYearP_rb);
        threeY = (RadioButton)v.findViewById(R.id.threeYearP_rb);
        oneY = (RadioButton)v.findViewById(R.id.oneYearP_rb);
        all = (RadioButton)v.findViewById(R.id.allP_rb);

        postCodeCheck = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";

        allTick = true;
        sevenTick = false;
        fiveTick = false;
        threeTick = false;
        oneTick = false;

        sevenY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sevenTick = true;
                fiveTick = false;
                threeTick = false;
                oneTick = false;
                allTick = false;
            }
        });


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTick = true;
                sevenTick = false;
                fiveTick = false;
                threeTick = false;
                oneTick= false;
            }
        });

        fiveY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiveTick = true;
                sevenTick = false;
                threeTick = false;
                oneTick = false;
                allTick = false;
            }
        });

        threeY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeTick = true;
                sevenTick = false;
                fiveTick = false;
                oneTick = false;
                allTick = false;
            }
        });

        oneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTick = true;
                sevenTick = false;
                fiveTick = false;
                threeTick = false;
                allTick = false;
            }
        });

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


        return v;
    }

    public void moveNextFragment(){

        String search = searchET.getText().toString();
        Pattern p = Pattern.compile(postCodeCheck);
        Matcher m = p.matcher(search.toUpperCase());
        boolean b = m.matches();
        if(searchET.equals("")){
            Toast.makeText(getActivity(), "Please enter address",
                    Toast.LENGTH_LONG).show();
        }
        else if (b == true){
            String address = searchET.getText().toString();
            Fragment fr = new PriceResultFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle args = new Bundle();
            args.putString("address", address);
            args.putBoolean("sevenYears", sevenTick);
            args.putBoolean("fiveYears", fiveTick);
            args.putBoolean("threeYears", threeTick);
            args.putBoolean("oneYear", oneTick);
            args.putBoolean("all", allTick);
            fr.setArguments(args);
            ft.replace(R.id.fragment_container, fr);
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            Toast.makeText(getActivity(), "Please ensure you are using UK postcode and space where appropirate",
                    Toast.LENGTH_LONG).show();
        }
    }

}
