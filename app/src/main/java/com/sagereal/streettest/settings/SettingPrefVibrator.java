package com.sagereal.streettest.settings;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.sagereal.streettest.R;

public class SettingPrefVibrator extends SettingPref {
    public static final String PREF_VIBRATOR_DURATION = "VIBRATOR_DURATION";
    private EditTextPreference edtVibratorDuration;
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            if (key.equals(PREF_VIBRATOR_DURATION)) {
                edtVibratorDuration.setSummary(newValue + "millisecond");
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
            this.edtVibratorDuration.setSummary(new StringBuilder(String.valueOf(this.edtVibratorDuration.getText())).append("   millisecond").toString());
            this.edtVibratorDuration.setOnPreferenceChangeListener(this.onPreferenceChangeListener);
        }
    }


