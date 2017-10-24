package com.sagereal.streettest.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.Log;

import com.sagereal.streettest.R;

public class SettingPrefVibrator extends SettingPref {
    public static final String PREF_VIBRATOR_DURATION = "VIBRATOR_DURATION";
    private EditTextPreference edtVibratorDuration;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            if (key.equals(PREF_VIBRATOR_DURATION)) {
                edtVibratorDuration.setSummary(newValue + "s");
                Log.e("licongasd", "" + newValue);
                edtVibratorDuration.setDefaultValue(newValue + "");
            }
            return true;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_vibrator);
        initViews();
    }

    private void initViews() {
        this.edtVibratorDuration = (EditTextPreference) findPreference(PREF_VIBRATOR_DURATION);
        this.edtVibratorDuration.setSummary(new StringBuilder(String.valueOf(this.edtVibratorDuration.getText())).append("s").toString());
        this.edtVibratorDuration.setOnPreferenceChangeListener(this.onPreferenceChangeListener);
    }

    public static int getVibratorDuration(Context context) {
        return Integer.parseInt(SettingPref.getSettingSharedPreferences(context).getString(PREF_VIBRATOR_DURATION, Integer.toString(1)));
    }

}
