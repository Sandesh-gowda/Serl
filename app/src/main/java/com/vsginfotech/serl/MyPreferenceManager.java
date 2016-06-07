package com.vsginfotech.serl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Constructor
    public MyPreferenceManager(Context context, String pref_key_value) {
        this._context = context;
        pref = _context.getSharedPreferences(pref_key_value, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User user) {
        editor.putString(Constants.mobile_number, user.getPhonenumber());
        editor.putString(Constants.tripid, user.getTripid());

        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getPhonenumber() + "");
    }

    public User getUser() {
        if (pref.getString(Constants.mobile_number, null) != null) {

            String mobile_number = pref.getString(Constants.mobile_number, null);
            Log.e("mobile_number", mobile_number + "");
            String tripid=pref.getString(Constants.tripid,null);
            Log.e("tripid", tripid + "");

            return new User(mobile_number,tripid);
        }
        return null;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
