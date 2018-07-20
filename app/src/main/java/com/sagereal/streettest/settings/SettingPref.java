package com.sagereal.streettest.settings;

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

    public static SharedPreferences getSettingSharedPreferences(Context context) {
        return context.getSharedPreferences(SETTING_PREF_NAME, Context.MODE_PRIVATE);
    }
}
