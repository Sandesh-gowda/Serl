package com.vsginfotech.serl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class MyLocation {


    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static boolean isGPSEnabled(Context context) {
        if (context instanceof Activity) {
            context = context.getApplicationContext();
        }
        LocationManager lm;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            try {
                if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public static boolean isLocationServiceAvailable(Context context) {
        if (context instanceof Activity) {
            context = context.getApplicationContext();
        }
        LocationManager lm;
//        if (lm == null) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        }

        try {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        try {
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                return true;
        } catch (Exception ex) {
        }

        return false;
    }


    public static boolean checkSelfPermissions(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
