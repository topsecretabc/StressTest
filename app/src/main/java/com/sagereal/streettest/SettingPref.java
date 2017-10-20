package com.sagereal.streettest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingPref extends PreferenceActivity {
    private static final String SETTING_PREF_NAME = "SettingPref";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(SETTING_PREF_NAME);
    }

    protected static SharedPreferences getSettingSharedPreferences(Context context) {
        return context.getSharedPreferences(SETTING_PREF_NAME, 0);
    }
}
