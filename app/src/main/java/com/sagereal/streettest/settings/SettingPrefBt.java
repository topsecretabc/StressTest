package com.sagereal.streettest.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.sagereal.streettest.R;

public class SettingPrefBt extends SettingPref {
    private static final String PREF_BT_DATATEST_DEVICE_ADDR = "BT_DATATEST_DEVICE_ADDR";
    private static final String PREF_BT_DATATEST_RETRY_TIMES = "BT_DATATEST_RETRY_TIMES";
    private static final String PREF_BT_ONOFFTEST_TURNOFF_DELAY = "BT_ONOFFTEST_TURNOFF_DELAY";
    private static final String PREF_BT_ONOFFTEST_TURNON_DELAY = "BT_ONOFFTEST_TURNON_DELAY";
    private static final String BT_DATATEST_DEVICE_ADDR_DEFAULT ="NONE";
    private EditTextPreference edtBtDataTestRetryTimes;
    private EditTextPreference edtBtOnOffTestTurnOffDelay;
    private EditTextPreference edtBtOnOffTestTurnOnDelay;
    private EditTextPreference edtBtDataTestDevAddr;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key=preference.getKey();
            if (key.equals(PREF_BT_ONOFFTEST_TURNOFF_DELAY)){
                edtBtOnOffTestTurnOffDelay.setSummary(newValue+"s");
                edtBtOnOffTestTurnOffDelay.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_BT_DATATEST_RETRY_TIMES)){
                edtBtDataTestRetryTimes.setSummary(newValue+"");
                edtBtDataTestRetryTimes.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_BT_ONOFFTEST_TURNON_DELAY)){
                edtBtOnOffTestTurnOnDelay.setSummary(newValue+"s");
                edtBtOnOffTestTurnOnDelay.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_BT_DATATEST_DEVICE_ADDR)){
                edtBtDataTestDevAddr.setSummary(newValue+"");
                edtBtDataTestDevAddr.setDefaultValue(newValue+"");
            }
            return true;
        }
    } ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_bt);
        initViews();
    }

    private void initViews() {
        edtBtDataTestDevAddr = (EditTextPreference) findPreference(PREF_BT_DATATEST_DEVICE_ADDR);
        edtBtDataTestDevAddr.setSummary(new StringBuilder(String.valueOf(this.edtBtDataTestDevAddr.getText())).toString());
        edtBtDataTestDevAddr.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtBtDataTestRetryTimes = (EditTextPreference) findPreference(PREF_BT_DATATEST_RETRY_TIMES);
        edtBtDataTestRetryTimes.setSummary(new StringBuilder(String.valueOf(this.edtBtDataTestRetryTimes.getText())).toString());
        edtBtDataTestRetryTimes.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtBtOnOffTestTurnOffDelay = (EditTextPreference) findPreference(PREF_BT_ONOFFTEST_TURNOFF_DELAY);
        edtBtOnOffTestTurnOffDelay.setSummary(new StringBuilder(String.valueOf(this.edtBtOnOffTestTurnOffDelay.getText())).append("s").toString());
        edtBtOnOffTestTurnOffDelay.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtBtOnOffTestTurnOnDelay = (EditTextPreference) findPreference(PREF_BT_ONOFFTEST_TURNON_DELAY);
        edtBtOnOffTestTurnOnDelay.setSummary(new StringBuilder(String.valueOf(this.edtBtOnOffTestTurnOnDelay.getText())).append("s").toString());
        edtBtOnOffTestTurnOnDelay.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

    public static String getBtDataTestDeviceAddress(Context context) {
        return SettingPref.getSettingSharedPreferences(context).getString(PREF_BT_DATATEST_DEVICE_ADDR, BT_DATATEST_DEVICE_ADDR_DEFAULT);
    }

    public static int getBtDataTestRetryTimes(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_BT_DATATEST_RETRY_TIMES, Integer.toString(3)));
    }

    public static int getBtOnOffTestTurnOffDelay(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_BT_ONOFFTEST_TURNOFF_DELAY, Integer.toString(5)));
    }

    public static int getBtOnOffTestTurnOnDelay(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_BT_ONOFFTEST_TURNON_DELAY, Integer.toString(5)));
    }

}
