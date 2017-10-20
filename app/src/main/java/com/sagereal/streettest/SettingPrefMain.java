package com.sagereal.streettest;

import android.content.Context;
import android.os.Bundle;

public class SettingPrefMain extends SettingPref {

    public static final String MAIN_BLUETOOTH = "MAIN_BT";
    public static final String MAIN_WIFI = "MAIN_WIFI";
    public static final String MAIN_GPS = "MAIN_GPS";
    public static final String MAIN_VIBRATE = "MAIN_VIBRATE";
    public static final String MAIN_BATTERY = "MAIN_BATTERY";
    public static final String MAIN_CAMERA = "MAIN_CAMERA";

    public static final String MAIN_COUNT = "MAIN_COUNT";
    public static final String MAIN_ISLOOP = "MAIN_ISLOOP";
    public static final String MAIN_RUNID = "MAIN_RUNID";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void onResume() {
        super.onResume();
    }


    public static boolean getBluetooth(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_BLUETOOTH, false);
    }

    public static boolean getWifi(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_WIFI, false);
    }

    public static boolean getGps(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_GPS, false);
    }

    public static boolean getVibrate(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_VIBRATE, false);
    }

    public static boolean getBattery(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_BATTERY, false);
    }

    public static boolean getCamera(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_CAMERA, false);
    }

    public static int getCount(Context context) {
        return getSettingSharedPreferences(context).getInt(MAIN_COUNT, 0);
    }

    public static boolean getIsloop(Context context) {
        return getSettingSharedPreferences(context).getBoolean(MAIN_ISLOOP, false);
    }

    public static String getRunid(Context context) {
        return getSettingSharedPreferences(context).getString(MAIN_RUNID, "");
    }

    public static void setBluetooth(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_BLUETOOTH, value).commit();
    }

    public static void setWifi(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_WIFI, value).commit();
    }

    public static void setGps(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_GPS, value).commit();
    }

    public static void setVibrate(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_VIBRATE, value).commit();
    }

    public static void setBattery(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_BATTERY, value).commit();
    }

    public static void setCamera(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_CAMERA, value).commit();
    }

    public static void setCount(Context context, int value) {
        getSettingSharedPreferences(context).edit().putInt(MAIN_COUNT, value).commit();
    }

    public static void setIsloop(Context context, boolean value) {
        getSettingSharedPreferences(context).edit().putBoolean(MAIN_ISLOOP, value).commit();
    }

    public static void setRunid(Context context, String value) {
        getSettingSharedPreferences(context).edit().putString(MAIN_RUNID, value).commit();
    }
}
