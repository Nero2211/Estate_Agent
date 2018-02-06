package com.example.nero.estateagent;

import java.io.Serializable;

/**
 * Created by Nero on 06/04/2017.
 */

public class Price {


    private String price7Years;
    private String price5Years;
    private String price3Years;
    private String price1Year;
    private String quantity7Years;
    private String quantity5Years;
    private String quantity3Years;
    private String quantity1Year;
    private String areaUrl;

    public Price(String p7years, String p5years, String p3years, String p1years,
                 String q7years, String q5years, String q3years, String q1year, String areaURL){
        this.price7Years = p7years;
        this.price5Years = p5years;
        this.price3Years = p3years;
        this.price1Year = p1years;
        this.quantity7Years = q7years;
        this.quantity5Years = q5years;
        this.quantity3Years = q3years;
        this.quantity1Year = q1year;
        this.areaUrl = areaURL;
    }

    public String getAreaUrl() {
        return areaUrl;
    }

    public String getPrice7Years() {
        return price7Years;
    }

    public String getPrice5Years() {
        return price5Years;
    }

    public String getPrice3Years() {
        return price3Years;
    }

    public String getPrice1Year() {
        return price1Year;
    }

    public String getQuantity7Years() {
        return quantity7Years;
    }

    public String getQuantity5Years() {
        return quantity5Years;
    }

    public String getQuantity3Years() {
        return quantity3Years;
    }

    public String getQuantity1Year() {
        return quantity1Year;
    }
}
