package com.example.nero.estateagent.com.nero.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Nero on 05/04/2017.
 */

public class AreaInfoSearchService extends AbstractService {

    private String query;
    private String api_key = "&api_key=sw3em55sbtvh8ngan66n3ddf";
    private ArrayList<String> results;


    public AreaInfoSearchService(String query){
        try{
            this.query = URLEncoder.encode(query, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getResults(){
        return results;
    }

    @Override
    public void run() {

        URL url;
        boolean error = false;
        HttpURLConnection httpURLConnection = null;
        StringBuilder result = new StringBuilder();

        try{
            url = new URL("http://api.zoopla.co.uk/api/v1/local_info_graphs.js?area="
                    + query + api_key);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));

            String line;
            while((line = bufferedReader.readLine()) != null){
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());
            String edu = jsonObject.getString("education_graph_url");
            String crime = jsonObject.getString("crime_graph_url");
            String tax = jsonObject.getString("council_tax_graph_url");
            String ppl = jsonObject.getString("people_graph_url");
            String st = jsonObject.getString("street");
            String town = jsonObject.getString("town");
            String postcode = jsonObject.getString("postcode");
            String county = jsonObject.getString("county");
            String country = jsonObject.getString("country");
            results = new ArrayList<>();
            results.add(edu);
            results.add(crime);
            results.add(tax);
            results.add(ppl);
            results.add(st);
            results.add(town);
            results.add(postcode);
            results.add(county);
            results.add(country);

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
