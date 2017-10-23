package com.sagereal.streettest.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;

import com.sagereal.streettest.R;

/**
 * Created by gms on 17-10-20.
 */

public class SettingPrefCamera extends SettingPref {
    public static final String PREF_CAMERA_BACKCAMERA_AUTOFOCUS = "CAMERA_BACKCAMERA_AUTOFOCUS";
    public static final String PREF_CAMERA_ENABLE_BACK_CAMERA_TEST = "CAMERA_ENABLE_BACK_CAMERA_TEST";
    public static final String PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST = "CAMERA_ENABLE_FRONT_CAMERA_TEST";
    private Preference.OnPreferenceChangeListener myPrefListener = new Preference.OnPreferenceChangeListener() {
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            if (key.equals(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST)) {
                swtCameraBackCameraAutoFocus.setEnabled(((Boolean) newValue).booleanValue());
            } else if (!key.equals(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST)) {
                key.equals(PREF_CAMERA_BACKCAMERA_AUTOFOCUS);
            }
            return true;
        }
    };
    private SwitchPreference swtCameraBackCameraAutoFocus;
    private SwitchPreference swtCameraEnableBackCameraTest;
    private SwitchPreference swtCameraEnableFrontCameraTest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_camera);
        initViews();
    }

    private void initViews() {
        swtCameraEnableBackCameraTest = (SwitchPreference) findPreference(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST);
        swtCameraEnableBackCameraTest.setOnPreferenceChangeListener(myPrefListener);
        swtCameraBackCameraAutoFocus = (SwitchPreference) findPreference(PREF_CAMERA_BACKCAMERA_AUTOFOCUS);
        swtCameraBackCameraAutoFocus.setOnPreferenceChangeListener(myPrefListener);
        swtCameraEnableFrontCameraTest = (SwitchPreference) findPreference(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST);
        swtCameraEnableFrontCameraTest.setOnPreferenceChangeListener(myPrefListener);
    }

    protected void onResume() {
        super.onResume();
        swtCameraBackCameraAutoFocus.setEnabled(swtCameraEnableBackCameraTest.isChecked());
    }

}
