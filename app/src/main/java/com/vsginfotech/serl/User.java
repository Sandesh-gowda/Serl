package com.vsginfotech.serl;

import java.io.Serializable;

/**
 * Created by Dell on 4/6/2016.
 */
public class User implements Serializable {

    private String tripid,mobile_number,driver_name;


    public User(String mobile_number, String tripid) {
        this.mobile_number=mobile_number;
        this.tripid=tripid;

    }


    public String getTripid() {
        return tripid;
    }

    public String getPhonenumber(){
        return mobile_number;
    }



}
