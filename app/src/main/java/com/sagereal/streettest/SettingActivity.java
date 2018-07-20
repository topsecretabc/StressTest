package com.sagereal.streettest;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.sagereal.streettest.settings.SettingPref;

public class SettingActivity extends SettingPref {
    private static final String RUN_TIMES = "RUN_TIMES";
    private static final String INTERVALS_TIME = "INTERVALS_TIME";
    private static final String RUN_OFF_TIME = "OFF_TIME";
    private EditTextPreference edtTestTimes;
    private EditTextPreference edtIntervalsTime;
    private EditTextPreference edtOffTime;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key=preference.getKey();
            if (key.equals(RUN_TIMES)){
                edtTestTimes.setSummary(newValue+"");
                edtTestTimes.setDefaultValue(newValue+"");
            }else if (key.equals(INTERVALS_TIME)){
                edtIntervalsTime.setSummary(newValue+"s");
                edtIntervalsTime.setDefaultValue(newValue+"");
            }else if (key.equals(RUN_OFF_TIME)){
                edtOffTime.setSummary(newValue+"s");
                edtOffTime.setDefaultValue(newValue+"");
            }
            return true;
        }
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_main);
        initViews();
    }

    private void initViews() {
        edtTestTimes = (EditTextPreference) findPreference(RUN_TIMES);
        edtTestTimes.setSummary(new StringBuilder(String.valueOf(edtTestTimes.getText())).toString());
        edtTestTimes.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtIntervalsTime = (EditTextPreference) findPreference(INTERVALS_TIME);
        edtIntervalsTime.setSummary(new StringBuilder(String.valueOf(edtIntervalsTime.getText())).append("s").toString());
        edtIntervalsTime.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtOffTime = (EditTextPreference) findPreference(RUN_OFF_TIME);
        edtOffTime.setSummary(new StringBuilder(String.valueOf(edtOffTime.getText())).append("s").toString());
        edtOffTime.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

    public static int getTestTimes(Context context) {
        return Integer.parseInt(getSettingSharedPreferences(context).getString(RUN_TIMES, Integer.toString(3)));
    }
    public static int getIntervalsTime(Context context) {
        return Integer.parseInt(getSettingSharedPreferences(context).getString(INTERVALS_TIME, Integer.toString(5)));
    }
    public static int getOffTime(Context context) {
        return Integer.parseInt(getSettingSharedPreferences(context).getString(RUN_OFF_TIME, Integer.toString(3)));
    }
}

