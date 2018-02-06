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
 * Created by Nero on 04/04/2017.
 */

public class PriceSearchService extends AbstractService {

    private String query;
    private String api_key = "&api_key=sw3em55sbtvh8ngan66n3ddf";
    private JSONArray results;


    public PriceSearchService(String query){
        try{
            this.query = URLEncoder.encode(query, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }

    public JSONArray getResults(){
        return results;
    }

    @Override
    public void run() {

        URL url;
        boolean error = false;
        HttpURLConnection httpURLConnection = null;
        StringBuilder result = new StringBuilder();

        //http://api.zoopla.co.uk/api/v1/average_sold_prices.js?postcode=b210ed&output_type=
        //county&area_type=streets&api_key=sw3em55sbtvh8ngan66n3ddf
        try{
            url = new URL("http://api.zoopla.co.uk/api/v1/average_sold_prices.js?postcode="
                    + query + "&output_type=county&area_type=streets" + api_key);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));

            String line;
            while((line = bufferedReader.readLine()) != null){
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());
            results = jsonObject.getJSONArray("areas");

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
