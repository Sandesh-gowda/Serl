package com.vsginfotech.serl;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;



/**
 * Created by SONU on 13/10/15.
 */
public class OkHttp_Class {

    public static final String TAG = "OkHTTP";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public JSONObject loginUser(String mobile_number, String driver_name) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("mobile_number", mobile_number)
                    .add("driver_name", driver_name)
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.Login_Url)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            Log.e(TAG, jsonString);
            JSONObject jsonData = new JSONObject(jsonString);
            //  Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public JSONObject find_location( String tripid,  String lan, String lat, String battery) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("tripid", tripid)

                    .add("lan", lan)
                    .add("lat", lat)
                    .add("battery", battery)


                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.Location_track)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            Log.e(TAG, jsonString);
            JSONObject jsonData = new JSONObject(jsonString);
            //  Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
