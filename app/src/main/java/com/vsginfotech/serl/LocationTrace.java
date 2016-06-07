package com.vsginfotech.serl;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by vsginfotech on 5/28/2016.
 */

public class LocationTrace extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final int TWO_MINUTES = 100 * 10;
    private Toast mToastToShow, toast2;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10; // 30 seconds
    private Timer timer = new Timer();
    private Context context;
    String easyPuzzle;
    double lat;
    double lan;
    int level;
    double latitude;
    double longitude;
    private Handler handler = new Handler();
    Location location = null;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    protected LocationManager locationManager;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.context = this;
        get_current_location();
//      Toast.makeText(context, "Lat"+latitude+"long"+longitude,Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }


    @Override
    public void onLocationChanged(final Location location) {
        if ((location != null) && (location.getLatitude() != 0) && (location.getLongitude() != 0)) {
            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            //  batterylevel();
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.e("Latitude value", latitude + "");
                            Log.e("Longitude Value", longitude + "");



                        }
                    });


                }
            };

            timer.schedule(timerTask, 0, 300000);

            batterylevel();

           /* if (!Utils.getuserid(context).equalsIgnoreCase("")) {
                Double[] arr = {location.getLatitude(), location.getLongitude()};

                // DO ASYNCTASK
            }*/
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*
    *  Get Current Location
    */
    public Location get_current_location() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {


        } else {
            if (isGPSEnabled) {

                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                                             Toast.makeText(context, "Latgps"+latitude+"long"+longitude,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            if (isNetworkEnabled) {

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null) {

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Toast.makeText(context, "Latgps1"+latitude+"long"+longitude,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        return location;
    }


    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }


    @Override
    public void onDestroy() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void batterylevel() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask backtask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
                                public void onReceive(Context context, Intent intent) {
                                    context.unregisterReceiver(this);
                                    int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                                    level = -1;
                                    if (currentLevel >= 0 && scale > 0) {
                                        level = (currentLevel * 100) / scale;
                                    }

                                    checkValidation();


                                }
                            };


                            IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                            registerReceiver(batteryLevelReceiver, batteryLevelFilter);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(backtask, 0, 300000); //execute in every 20000 ms*/;


    }

    private void checkValidation() {

        easyPuzzle = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser().getTripid();

        Log.e("Array value", easyPuzzle + "");

        String tripid;
        String driverid;
        String createddate;
        String databalance;
        String latitude = Double.toString(lat);


        String longitude = Double.toString(lan);
        Log.e("lan Size", lan + "");
        Log.e("lat Size", lat + "");

        String battery = Integer.toString(level);
        Log.e("Array Size", battery + "");

        if (MyApplication.getInstance().IsInternetConnected())
            new ContactUsTask(easyPuzzle, latitude, longitude, battery).execute();

        else
            Toast.makeText(this, this.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

    }

    private class ContactUsTask extends AsyncTask<Void, Void, JSONObject> {
        private String tripid, lan, lat, battery;


        public ContactUsTask(String tripid, String lan, String lat, String battery) {
            this.tripid = tripid;

            this.lan = lan;
            this.lat = lat;
            this.battery = battery;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         /*   dialog = new ProgressDialog(Gpssetting.this);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
      //      dialog.setMessage("Contacting...Please wait...");
            dialog.show();*/
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                json = new OkHttp_Class().find_location(tripid, lan, lat, battery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
           /* if (dialog.isShowing())
                dialog.dismiss();*/
            try {
                if (jsonObject != null) {
                    //   resetAllViews();
                    mToastToShow = Toast.makeText(LocationTrace.this, "Tracking", Toast.LENGTH_SHORT);
                    mToastToShow.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mToastToShow.cancel();
                        }
                    }, 2000);


                } else
                    Toast.makeText(LocationTrace.this, "Failed to Connect right now", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LocationTrace.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            /*if (dialog.isShowing())
                dialog.dismiss();*/
        }


    }




}