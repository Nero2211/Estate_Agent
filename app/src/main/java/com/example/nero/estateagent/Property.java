package com.example.nero.estateagent;

import java.io.Serializable;

/**
 * Created by Nero on 03/04/2017.
 */

public class Property implements Serializable{

    private transient int position;

    private String thumbnailURL;
    private String largeImageURL;
    private String address;
    private String price;
    private String bedrooms;
    private String description;
    private String latitude;
    private String longitude;
    private String type;
    private String agent;
    private String agentAddress;
    private String category;
    private String listingID;
    private String street;
    private boolean imageNull;


    public Property(String thumbnailURL, String largeImageURL, String address,
                    String type, String agent, String price, String bedrooms, String description,
                    String agentAddress, String category, String latitude, String longitude, String listing, String st, boolean image){

        this.thumbnailURL = thumbnailURL;
        this.largeImageURL = largeImageURL;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.agentAddress = agentAddress;
        this.type = type;
        this.agent = agent;
        this.price = price;
        this.bedrooms = bedrooms;
        this.category = category;
        this.listingID = listing;
        this.street = st;
        this.imageNull = image;
    }

    public void setImageNull(boolean imageNull) {
        this.imageNull = imageNull;
    }

    public String getStreet() {
        return street;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getListingID() {
        return listingID;
    }

    public boolean isImageNull() {
        return imageNull;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public String getType() {
        return type;
    }

    public String getAgent() {
        return agent;
    }

    public String getPrice() {
        return price;
    }

    public String getBedrooms() {
        return bedrooms;
    }
}
