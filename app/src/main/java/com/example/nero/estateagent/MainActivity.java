package com.example.nero.estateagent;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nero.estateagent.AreaInfoSearchFragment.AreaInfoSearchFragment;
import com.example.nero.estateagent.ContactUsFragment.ContactUsFragment;
import com.example.nero.estateagent.CustomAdapter.CustomPropertyAdapter;
import com.example.nero.estateagent.FavouritePropertyFragment.FavouriteListFragment;
import com.example.nero.estateagent.PriceSearchFragment.PriceSearchFragment;
import com.example.nero.estateagent.PropertySearchFragments.PropertySearchFragment;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.System.getProperties;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements Serializable{


    FragmentManager fm;
    TextView favBtn, searchBtn, priceBtn, areaBtn, contactBtn;
    Boolean favourite = false;
    Boolean search = false;
    Boolean price = false;
    Boolean area = false;
    Boolean contactus = false;
    ArrayList<Property> savedProperties;
    ArrayList<Property> tempArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favBtn = (TextView)findViewById(R.id.favourite_button);
        searchBtn = (TextView)findViewById(R.id.search_button);
        priceBtn = (TextView)findViewById(R.id.prices_button);
        areaBtn = (TextView)findViewById(R.id.area_button);
        contactBtn = (TextView)findViewById(R.id.contactus_button);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        fm = getSupportFragmentManager();
        Fragment fragment;


        getProperties();

        /*if(savedProperties.isEmpty()){
            fragment = new PropertySearchFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            searchBtn.setTypeface(null, Typeface.BOLD);
            search = true;
            //================================================================
            priceBtn.setTypeface(null, Typeface.NORMAL);
            areaBtn.setTypeface(null,Typeface.NORMAL);
            price=false;
            area=false;
        }
        else{*/
        try{
            if(savedProperties.isEmpty()){
                fragment = new PropertySearchFragment();
                fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
                searchBtn.setTypeface(null, Typeface.BOLD);
                search = true;
                //================================================================
                priceBtn.setTypeface(null, Typeface.NORMAL);
                areaBtn.setTypeface(null,Typeface.NORMAL);
                price=false;
                area=false;
            }
            else{
                displayFragment(new FavouriteListFragment());
                favBtn.setTypeface(null, Typeface.BOLD);
                favourite = true;
                //================================================================
                priceBtn.setTypeface(null, Typeface.NORMAL);
                areaBtn.setTypeface(null, Typeface.NORMAL);
                searchBtn.setTypeface(null, Typeface.NORMAL);
                contactBtn.setTypeface(null, Typeface.NORMAL);
                contactus = false;
                price = false;
                area = false;
                search = false;
            }
        }catch (Exception ex){
            fragment = new PropertySearchFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            searchBtn.setTypeface(null, Typeface.BOLD);
            search = true;
            //================================================================
            priceBtn.setTypeface(null, Typeface.NORMAL);
            areaBtn.setTypeface(null,Typeface.NORMAL);
            price=false;
            area=false;
        }
//            displayFragment(new FavouriteListFragment());
//            favBtn.setTypeface(null, Typeface.BOLD);
//            favourite = true;
//            //================================================================
//            priceBtn.setTypeface(null, Typeface.NORMAL);
//            areaBtn.setTypeface(null, Typeface.NORMAL);
//            searchBtn.setTypeface(null, Typeface.NORMAL);
//            contactBtn.setTypeface(null, Typeface.NORMAL);
//            contactus = false;
//            price = false;
//            area = false;
//            search = false;


        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favourite == false) {
                    displayFragment(new FavouriteListFragment());
                    favBtn.setTypeface(null, Typeface.BOLD);
                    favourite = true;
                    //================================================================
                    priceBtn.setTypeface(null, Typeface.NORMAL);
                    areaBtn.setTypeface(null, Typeface.NORMAL);
                    searchBtn.setTypeface(null, Typeface.NORMAL);
                    contactBtn.setTypeface(null, Typeface.NORMAL);
                    contactus = false;
                    price = false;
                    area = false;
                    search = false;
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search == false) {
                    displayFragment(new PropertySearchFragment());
                    searchBtn.setTypeface(null, Typeface.BOLD);
                    search = true;
        //================================================================
                    priceBtn.setTypeface(null, Typeface.NORMAL);
                    areaBtn.setTypeface(null, Typeface.NORMAL);
                    favBtn.setTypeface(null, Typeface.NORMAL);
                    contactBtn.setTypeface(null, Typeface.NORMAL);
                    contactus = false;
                    favourite = false;
                    price = false;
                    area = false;
                }
            }
        });

        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price == false){
                    displayFragment(new PriceSearchFragment());
                    priceBtn.setTypeface(null, Typeface.BOLD);
                    price=true;
        //================================================================
                    searchBtn.setTypeface(null,Typeface.NORMAL);
                    areaBtn.setTypeface(null,Typeface.NORMAL);
                    favBtn.setTypeface(null, Typeface.NORMAL);
                    contactBtn.setTypeface(null, Typeface.NORMAL);
                    contactus = false;
                    favourite = false;
                    search=false;
                    area=false;
                }
            }
        });

        areaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area == false) {
                    displayFragment(new AreaInfoSearchFragment());
                    areaBtn.setTypeface(null, Typeface.BOLD);
                    area = true;
        //================================================================
                    searchBtn.setTypeface(null, Typeface.NORMAL);
                    priceBtn.setTypeface(null, Typeface.NORMAL);
                    favBtn.setTypeface(null, Typeface.NORMAL);
                    contactBtn.setTypeface(null, Typeface.NORMAL);
                    contactus = false;
                    favourite = false;
                    search = false;
                    price = false;
                }
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactus == false) {
                    displayFragment(new ContactUsFragment());
                    contactBtn.setTypeface(null, Typeface.BOLD);
                    contactus = true;
                    //================================================================
                    searchBtn.setTypeface(null, Typeface.NORMAL);
                    priceBtn.setTypeface(null, Typeface.NORMAL);
                    favBtn.setTypeface(null, Typeface.NORMAL);
                    areaBtn.setTypeface(null, Typeface.NORMAL);
                    area = false;
                    favourite = false;
                    search = false;
                    price = false;
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.warning2)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void displayFragment(Fragment myFragment){
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, myFragment).commit();
    }

    public void getProperties(){
        try{
            FileInputStream fis = openFileInput("myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);

            tempArrayList = (ArrayList) ois.readObject();
                savedProperties = new ArrayList<>();
                for (Property property : tempArrayList) {
                    Property item = new Property(property.getThumbnailURL(), property.getLargeImageURL(),
                            property.getAddress(), property.getType(), property.getAgent(), property.getPrice(),
                            property.getBedrooms(), property.getDescription(), property.getAgentAddress(),
                            property.getCategory(), property.getLatitude(), property.getLongitude(), property.getListingID(),
                            property.getStreet(), property.isImageNull());
                    savedProperties.add(item);
                }
                ois.close();
        }
        catch(IOException ioe){
        }
        catch(ClassNotFoundException c){
        }
    }
}
