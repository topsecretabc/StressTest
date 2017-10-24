package com.sagereal.streettest.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;

import com.sagereal.streettest.R;


public class SettingPrefCamera extends SettingPref {
    public static final String PREF_CAMERA_BACKCAMERA_AUTOFOCUS = "CAMERA_BACKCAMERA_AUTOFOCUS";
    public static final String PREF_CAMERA_ENABLE_BACK_CAMERA_TEST = "CAMERA_ENABLE_BACK_CAMERA_TEST";
    public static final String PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST = "CAMERA_ENABLE_FRONT_CAMERA_TEST";
    private OnPreferenceChangeListener myPrefListener = new OnPreferenceChangeListener() {
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            if (key.equals(SettingPrefCamera.PREF_CAMERA_ENABLE_BACK_CAMERA_TEST)) {
                SettingPrefCamera.this.swtCameraBackCameraAutoFocus.setEnabled(((Boolean) newValue).booleanValue());
            } else if (!key.equals(SettingPrefCamera.PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST)) {
                key.equals(SettingPrefCamera.PREF_CAMERA_BACKCAMERA_AUTOFOCUS);
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
        this.swtCameraEnableBackCameraTest = (SwitchPreference) findPreference(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST);
        this.swtCameraEnableBackCameraTest.setOnPreferenceChangeListener(this.myPrefListener);
        this.swtCameraBackCameraAutoFocus = (SwitchPreference) findPreference(PREF_CAMERA_BACKCAMERA_AUTOFOCUS);
        this.swtCameraBackCameraAutoFocus.setOnPreferenceChangeListener(this.myPrefListener);
        this.swtCameraEnableFrontCameraTest = (SwitchPreference) findPreference(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST);
        this.swtCameraEnableFrontCameraTest.setOnPreferenceChangeListener(this.myPrefListener);
    }

    protected void onResume() {
        super.onResume();
        this.swtCameraBackCameraAutoFocus.setEnabled(this.swtCameraEnableBackCameraTest.isChecked());
    }



    public static Boolean getEnableCameraBackCameraTest(Context context) {
        return getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST, true);
    }



    public static Boolean getEnableCameraFrontCameraTest(Context context) {
        return getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST, true);
    }



    public static Boolean getEnableCameraBackCameraAutoFocus(Context context) {
        return getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_BACKCAMERA_AUTOFOCUS, true);
    }


}
