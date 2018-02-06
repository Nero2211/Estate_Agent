package com.example.nero.estateagent.FavouritePropertyFragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nero.estateagent.CustomAdapter.CustomPropertyAdapter;
import com.example.nero.estateagent.CustomAdapter.CustomPropertyRecyclerAdapter;
import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.PropertySearchFragments.PropertyDescriptionFragment;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.interfaces.CustomPropertyInteractor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteListFragment extends Fragment implements Serializable, CustomPropertyInteractor{

    RecyclerView propertiesListview;
    ArrayList<Property> tempArrayList;
    ArrayList<Property> savedProperties;
    TextView result;


    public FavouriteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourite_list, container, false);
        propertiesListview = (RecyclerView) v.findViewById(R.id.favourite_property_listView);
        propertiesListview.setLayoutManager(new LinearLayoutManager(getContext()));
        result = (TextView)v.findViewById(R.id.favourite_list_textView);

        getProperties();

//        propertiesListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Fragment fr = new FavouriteDescriptionFragment();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Bundle args = new Bundle();
//                args.putInt("position", position);
//                args.putString("address", savedProperties.get(position).getAddress());
//                args.putString("URL", savedProperties.get(position).getLargeImageURL());
//                args.putString("thumbnailURL", savedProperties.get(position).getThumbnailURL());
//                args.putString("price", savedProperties.get(position).getPrice());
//                args.putString("bedrooms", savedProperties.get(position).getBedrooms());
//                args.putString("description", savedProperties.get(position).getDescription());
//                args.putString("type", savedProperties.get(position).getType());
//                args.putString("category", savedProperties.get(position).getCategory());
//                args.putString("agentname", savedProperties.get(position).getAgent());
//                args.putString("agentaddress", savedProperties.get(position).getAgentAddress());
//                args.putString("latitude", savedProperties.get(position).getLatitude());
//                args.putString("longitude", savedProperties.get(position).getLongitude());
//                fr.setArguments(args);
//                ft.replace(R.id.fragment_container, fr);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });
//
//        propertiesListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                alert.setTitle("DELETE");
//                alert.setMessage("Are you sure you wish to delete the property from your favourite list?");
//                //buttons for the dialog
//                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteSelectedProperty(position);
//                        getProperties();
//                    }
//                });
//
//                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                alert.show();
//                return true;
//            }
//        });
        return v;
    }

    public void getProperties(){
        try{
            FileInputStream fis = getContext().openFileInput("myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);

            tempArrayList = (ArrayList) ois.readObject();
            if(tempArrayList.isEmpty()) {
                result.setText("No Properties Saved");
                result.setVisibility(View.VISIBLE);
                propertiesListview.setVisibility(View.GONE);
            }
            else{

                result.setVisibility(View.GONE);
                savedProperties = new ArrayList<>();
                for (Property property : tempArrayList) {
                    Property item = new Property(property.getThumbnailURL(), property.getLargeImageURL(),
                            property.getAddress(), property.getType(), property.getAgent(), property.getPrice(),
                            property.getBedrooms(), property.getDescription(), property.getAgentAddress(),
                            property.getCategory(), property.getLatitude(), property.getLongitude(), property.getListingID(), property.getStreet(), property.isImageNull());

                    savedProperties.add(item);
                }


                CustomPropertyRecyclerAdapter adapter = new CustomPropertyRecyclerAdapter(getContext(), savedProperties);
                adapter.setInteractor(this);
                propertiesListview.setAdapter(adapter);
                ois.close();
            }
        }
        catch(IOException ioe){
        }
        catch(ClassNotFoundException c){
        }
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

    public int propertyCount(){
        return savedProperties.size();
    }

    @Override
    public void OpenPropertyDescription(Property property) {
        Fragment fr = new FavouriteDescriptionFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();
        args.putInt("position", property.getPosition());
        args.putString("address", property.getAddress());
        args.putString("URL", property.getLargeImageURL());
        args.putString("thumbnailURL", property.getThumbnailURL());
        args.putString("price", property.getPrice());
        args.putString("bedrooms", property.getBedrooms());
        args.putString("description", property.getDescription());
        args.putString("type", property.getType());
        args.putString("category", property.getCategory());
        args.putString("agentname", property.getAgent());
        args.putString("agentaddress", property.getAgentAddress());
        args.putString("latitude", property.getLatitude());
        args.putString("longitude", property.getLongitude());
        args.putSerializable("street", property.getStreet());
        fr.setArguments(args);
        ft.replace(R.id.fragment_container, fr);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onLongClick(final Property property) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("DELETE");
            alert.setMessage("Are you sure you wish to delete the property from your favourite list?");
            //buttons for the dialog
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteSelectedProperty(property.getPosition());
                    getProperties();
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
}
