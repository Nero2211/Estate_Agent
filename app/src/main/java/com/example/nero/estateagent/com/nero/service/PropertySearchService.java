package com.example.nero.estateagent.com.nero.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Nero on 20/03/2017.
 */

public class PropertySearchService extends AbstractService {

    private String address, minimumBedrooms, maximumBedrooms, minimumPrice, maximumPrice;
    private String api_key = "&api_key=sw3em55sbtvh8ngan66n3ddf";
    private JSONArray results;
//    String maxBed, String minPrice, String maxPrice, String propType

    public PropertySearchService(String query, String minBed, String maxBed, String minPrice, String maxPrice){
        try{
            this.address = URLEncoder.encode(query, "UTF-8");
            this.minimumBedrooms = URLEncoder.encode(minBed, "UTF-8");
            this.maximumBedrooms = URLEncoder.encode(maxBed, "UTF-8");
            this.minimumPrice = URLEncoder.encode(minPrice, "UTF-8");
            this.maximumPrice = URLEncoder.encode(maxPrice, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }

    public JSONArray getResults(){
        return results;
    }


    @Override
    public void run(){
        URL url;
        boolean error = false;
        HttpURLConnection httpURLConnection = null;
        StringBuilder result = new StringBuilder();

        try{
            url = new URL("http://api.zoopla.co.uk/api/v1/property_listings.js?area="
                    + address + "&minimum_beds=" + minimumBedrooms + "&maximum_beds="
                    + maximumBedrooms + "&minimum_price=" + minimumPrice + "&maximum_price="
                    + maximumPrice + api_key);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));

            String line;
            while((line = bufferedReader.readLine()) != null){
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());
            results = jsonObject.getJSONArray("listing");

        }catch (Exception ex){
            ex.printStackTrace();
            results = null;
            error = true;
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }

        super.serviceCallComplete(error);
    }
}
