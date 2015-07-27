package com.studio.chat.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefrence {

    static Prefrence mInstance = null;

    private static final String PREF_NAME = "chatmodule.xml";

    private static final int PREF_MODE = Context.MODE_PRIVATE;

    private Context mContext;

    private SharedPreferences mSharedPrefrence = null;

    private Prefrence(Context aContext) {
        if (mInstance == null) mInstance = this;
        this.mContext = aContext;
        openPrefrence();
    }

    public static void voidInstance() {
        mInstance = null;
    }

    public static Prefrence getInstance(Context acontext) {
        return new Prefrence(acontext);
    }

    private void openPrefrence() {
        if (!hasPrefExist()) {
            mSharedPrefrence = mContext.getSharedPreferences(PREF_NAME, PREF_MODE);
        }
    }

    private void closePrefrence() {
        if (hasPrefExist()) {
            mSharedPrefrence = null;
        }
    }

    private boolean hasPrefExist() {
        return mSharedPrefrence != null ? true : false;
    }

    private SharedPreferences getPrefInstance() {
        openPrefrence();
        return mSharedPrefrence;
    }

    public void setValue(String key, String value) {
        SharedPreferences.Editor prefsPrivateEditor = getPrefInstance().edit();
        prefsPrivateEditor.putString(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        closePrefrence();
    }

    public String getValue(String key, String defaultValue) {
        String result = getPrefInstance().getString(key, defaultValue);
        closePrefrence();
        return result;
    }

    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor prefsPrivateEditor = getPrefInstance().edit();
        prefsPrivateEditor.putBoolean(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        closePrefrence();
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        boolean result = getPrefInstance().getBoolean(key, defaultValue);
        closePrefrence();
        return result;
    }

    public void setIntValue(String key, int value) {
        SharedPreferences.Editor prefsPrivateEditor = getPrefInstance().edit();
        prefsPrivateEditor.putInt(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        closePrefrence();
    }

    public int getIntValue(String key, int defaultValue) {
        int result = getPrefInstance().getInt(key, defaultValue);
        closePrefrence();
        return result;
    }

    //*****************************************
    // All application direct preference we access from here
    //****************************************

    public static void updateCurrentUser(Context context, String fromUser){
        Prefrence.getInstance(context).setValue(Constants.PREF_KEY_FROM_USER,fromUser);
    }

    public static String getCurrentUser(Context context,String defaultValue){
        return  Prefrence.getInstance(context).getValue(Constants.PREF_KEY_FROM_USER, defaultValue);
    }

    public static void updateSelectUser(Context context, String toUser){
        Prefrence.getInstance(context).setValue(Constants.PREF_KEY_TO_USER,toUser);
    }

    public static String getSelectUser(Context context,String defaultValue){
        return  Prefrence.getInstance(context).getValue(Constants.PREF_KEY_TO_USER, defaultValue);
    }

}
