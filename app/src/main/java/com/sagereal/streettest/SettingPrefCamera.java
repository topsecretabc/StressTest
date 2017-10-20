package com.sagereal.streettest;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;

/**
 * Created by gms on 17-10-20.
 */

public class SettingPrefCamera extends SettingPref {
    private static final Boolean CAMERA_BACKCAMREA_AUTOFOCUS_DEFAULT = Boolean.valueOf(false);
    private static final Boolean CAMERA_ENABLE_BACK_CAMERA_TEST_DEFAULT = Boolean.valueOf(false);
    private static final Boolean CAMERA_ENABLE_FRONT_CAMERA_TEST_DEFAULT = Boolean.valueOf(false);
    private static final Boolean ENABLE_CAMERA_TEST_DEFAULT = Boolean.valueOf(false);
    public static final String JSON_KEY = "Camera";
    private static final String JSON_KEY_CAMERA_PHOTOCPTTEST = "PhotoCapture";
    private static final String JSON_KEY_ENABLE_CAMERA_TEST = "Enable";
    private static final String JSON_KEY_PHOTOCPTTEST_BACKCAMERA_AUTOFOCUS = "BackCameraAutoFocus";
    private static final String JSON_KEY_PHOTOCPTTEST_ENABLE_BACK_CAMERA_TEST = "BackCameraTest";
    private static final String JSON_KEY_PHOTOCPTTEST_ENABLE_FRONT_CAMERA_TEST = "FrontCameraTest";
    public static final String PREF_CAMERA_BACKCAMERA_AUTOFOCUS = "CAMERA_BACKCAMERA_AUTOFOCUS";
    public static final String PREF_CAMERA_ENABLE_BACK_CAMERA_TEST = "CAMERA_ENABLE_BACK_CAMERA_TEST";
    public static final String PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST = "CAMERA_ENABLE_FRONT_CAMERA_TEST";
    public static final String PREF_ENABLE_CAMERA_TEST = "ENABLE_CAMERA_TEST";
    private Preference.OnPreferenceChangeListener myPrefListener = new Preference.OnPreferenceChangeListener() {
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
       // addPreferencesFromResource(R.xml.pref_setting_camera);
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

    public static void reset(Context context) {
        setEnableCameraTest(context, ENABLE_CAMERA_TEST_DEFAULT);
        setEnableCameraBackCameraTest(context, CAMERA_ENABLE_BACK_CAMERA_TEST_DEFAULT);
        setEnableCameraFrontCameraTest(context, CAMERA_ENABLE_FRONT_CAMERA_TEST_DEFAULT);
        setEnableCameraFrontCameraTest(context, CAMERA_BACKCAMREA_AUTOFOCUS_DEFAULT);
    }

 /*   public static JSONObject getJson(Context context) {
        try {
            JSONObject json = new JSONObject();
            json.put(JSON_KEY_ENABLE_CAMERA_TEST, getEnableCameraTest(context));
            JSONObject node = new JSONObject();
            node.put(JSON_KEY_PHOTOCPTTEST_ENABLE_BACK_CAMERA_TEST, getEnableCameraBackCameraTest(context));
            node.put(JSON_KEY_PHOTOCPTTEST_ENABLE_FRONT_CAMERA_TEST, getEnableCameraFrontCameraTest(context));
            node.put(JSON_KEY_PHOTOCPTTEST_BACKCAMERA_AUTOFOCUS, getEnableCameraBackCameraAutoFocus(context));
            json.put(JSON_KEY_CAMERA_PHOTOCPTTEST, node);
            return json;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setPref(Context context, JSONObject json) {
        try {
            setEnableCameraTest(context, Boolean.valueOf(json.getBoolean(JSON_KEY_ENABLE_CAMERA_TEST)));
            JSONObject node = json.getJSONObject(JSON_KEY_CAMERA_PHOTOCPTTEST);
            setEnableCameraBackCameraTest(context, Boolean.valueOf(node.getBoolean(JSON_KEY_PHOTOCPTTEST_ENABLE_BACK_CAMERA_TEST)));
            setEnableCameraFrontCameraTest(context, Boolean.valueOf(node.getBoolean(JSON_KEY_PHOTOCPTTEST_ENABLE_FRONT_CAMERA_TEST)));
            setEnableCameraBackCameraAutoFocus(context, Boolean.valueOf(node.getBoolean(JSON_KEY_PHOTOCPTTEST_BACKCAMERA_AUTOFOCUS)));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }*/

    public static Boolean getEnableCameraTest(Context context) {
        return Boolean.valueOf(SettingPref.getSettingSharedPreferences(context).getBoolean(PREF_ENABLE_CAMERA_TEST, ENABLE_CAMERA_TEST_DEFAULT.booleanValue()));
    }

    public static void setEnableCameraTest(Context context, Boolean enable) {
        SettingPref.getSettingSharedPreferences(context).edit().putBoolean(PREF_ENABLE_CAMERA_TEST, enable.booleanValue()).commit();
    }

    public static Boolean getEnableCameraBackCameraTest(Context context) {
        return Boolean.valueOf(SettingPref.getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST, CAMERA_ENABLE_BACK_CAMERA_TEST_DEFAULT.booleanValue()));
    }

    public static void setEnableCameraBackCameraTest(Context context, Boolean enable) {
        SettingPref.getSettingSharedPreferences(context).edit().putBoolean(PREF_CAMERA_ENABLE_BACK_CAMERA_TEST, enable.booleanValue()).commit();
    }

    public static Boolean getEnableCameraFrontCameraTest(Context context) {
        return Boolean.valueOf(SettingPref.getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST, CAMERA_ENABLE_FRONT_CAMERA_TEST_DEFAULT.booleanValue()));
    }

    public static void setEnableCameraFrontCameraTest(Context context, Boolean enable) {
        SettingPref.getSettingSharedPreferences(context).edit().putBoolean(PREF_CAMERA_ENABLE_FRONT_CAMERA_TEST, enable.booleanValue()).commit();
    }

    public static Boolean getEnableCameraBackCameraAutoFocus(Context context) {
        return Boolean.valueOf(SettingPref.getSettingSharedPreferences(context).getBoolean(PREF_CAMERA_BACKCAMERA_AUTOFOCUS, CAMERA_ENABLE_FRONT_CAMERA_TEST_DEFAULT.booleanValue()));
    }

    public static void setEnableCameraBackCameraAutoFocus(Context context, Boolean enable) {
        SettingPref.getSettingSharedPreferences(context).edit().putBoolean(PREF_CAMERA_BACKCAMERA_AUTOFOCUS, enable.booleanValue()).commit();
    }
}
