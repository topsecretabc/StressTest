package com.sagereal.streettest.settings;


import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.sagereal.streettest.R;

public class SettingPrefNet extends SettingPref {
    private static final String PREF_NET_ECHOTEST_ADDR = "NET_ECHOTEST_ADDR";
    private static final String PREF_NET_ECHOTEST_PORT = "NET_ECHOTEST_PORT";
    private static final String PREF_NET_ONOFFTEST_TURNOFF_DELAY = "NET_ONOFFTEST_TURNOFF_DELAY";
    private static final String PREF_NET_ONOFFTEST_TURNON_DELAY = "NET_ONOFFTEST_TURNON_DELAY";
    private static final String PREF_NET_PING_ADDR = "NET_PING_ADDR";
    private static final String PREF_NET_PING_TIMEOUT = "NET_PING_TIMEOUT";
    public static final String PREF_NET_ECHOTEST_DATA_REPEATTIMES = "NET_ECHOTEST_DATA_REPEATTIMES";
    private EditTextPreference edtNetAddr;
    private EditTextPreference edtNetEchoTestAddr;
    private EditTextPreference edtNetEchoTestPort;
    private EditTextPreference edtNetOnOffTestTurnOffDelay;
    private EditTextPreference edtNetOnOffTestTurnOnDelay;
    private EditTextPreference edtNetPingTimeout;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key=preference.getKey();
            if (key.equals(PREF_NET_ECHOTEST_ADDR)){
                edtNetEchoTestAddr.setSummary(newValue+"");
                edtNetEchoTestAddr.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_NET_ECHOTEST_PORT)){
                edtNetEchoTestPort.setSummary(newValue+"");
                edtNetEchoTestPort.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_NET_ONOFFTEST_TURNOFF_DELAY)){
                edtNetOnOffTestTurnOffDelay.setSummary(newValue+"s");
                edtNetOnOffTestTurnOffDelay.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_NET_ONOFFTEST_TURNON_DELAY)){
                edtNetOnOffTestTurnOnDelay.setSummary(newValue+"s");
                edtNetOnOffTestTurnOnDelay.setDefaultValue(newValue+"");
            }else if (key.equals(PREF_NET_PING_ADDR)){
                edtNetAddr.setSummary(newValue+"");
                edtNetAddr.setDefaultValue(newValue+"");
            }
            else if (key.equals(PREF_NET_PING_TIMEOUT)){
                edtNetPingTimeout.setSummary(newValue+"");
                edtNetPingTimeout.setDefaultValue(newValue+"");
            }
            return true;
        }
    } ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_net);
        initViews();
    }

    private void initViews() {
        edtNetEchoTestAddr = (EditTextPreference) findPreference(PREF_NET_ECHOTEST_ADDR);
        edtNetEchoTestAddr.setSummary(new StringBuilder(String.valueOf(this.edtNetEchoTestAddr.getText())).toString());
        edtNetEchoTestAddr.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtNetEchoTestPort = (EditTextPreference) findPreference(PREF_NET_ECHOTEST_PORT);
        edtNetEchoTestPort.setSummary(new StringBuilder(String.valueOf(this.edtNetEchoTestPort.getText())).toString());
        edtNetEchoTestPort.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtNetOnOffTestTurnOffDelay = (EditTextPreference) findPreference(PREF_NET_ONOFFTEST_TURNOFF_DELAY);
        edtNetOnOffTestTurnOffDelay.setSummary(new StringBuilder(String.valueOf(this.edtNetOnOffTestTurnOffDelay.getText())).append("s").toString());
        edtNetOnOffTestTurnOffDelay.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtNetOnOffTestTurnOnDelay = (EditTextPreference) findPreference(PREF_NET_ONOFFTEST_TURNON_DELAY);
        edtNetOnOffTestTurnOnDelay.setSummary(new StringBuilder(String.valueOf(this.edtNetOnOffTestTurnOnDelay.getText())).append("s").toString());
        edtNetOnOffTestTurnOnDelay.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtNetAddr = (EditTextPreference) findPreference(PREF_NET_PING_ADDR);
        edtNetAddr.setSummary(new StringBuilder(String.valueOf(this.edtNetAddr.getText())).toString());
        edtNetAddr.setOnPreferenceChangeListener(onPreferenceChangeListener);
        edtNetPingTimeout = (EditTextPreference) findPreference(PREF_NET_PING_TIMEOUT);
        edtNetPingTimeout.setSummary(new StringBuilder(String.valueOf(this.edtNetPingTimeout.getText())).toString());
        edtNetPingTimeout.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

    public static int getNetEchoTestDataRepeatTimes(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_ECHOTEST_DATA_REPEATTIMES, Integer.toString(8)));
    }

    public static String getNetPingAddr(Context context) {
        return SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_PING_ADDR, "127.0.0.1");
    }

    public static int getNetPingTimeout(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_PING_TIMEOUT, Integer.toString(3)));
    }

    public static String getNetEchoTestAddr(Context context) {
        return SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_ECHOTEST_ADDR, "127.0.0.1");
    }

    public static int getNetEchoTestPort(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_ECHOTEST_PORT, Integer.toString(1024)));
    }

    public static int getNetOnOffTestTurnOffDelay(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_ONOFFTEST_TURNOFF_DELAY, Integer.toString(5)));
    }

    public static int getNetOnOffTestTurnOnDelay(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_NET_ONOFFTEST_TURNON_DELAY, Integer.toString(5)));
    }
}

