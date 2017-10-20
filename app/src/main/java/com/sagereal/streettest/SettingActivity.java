package com.sagereal.streettest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    protected Preference Vibrator_Duration, Run_Delay_After, Continues_Num;
    private SharedPreferences.Editor edit;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting_main);
        Vibrator_Duration = findPreference("Vibrator_Duration");
        Run_Delay_After = findPreference("Run_Delay_After");
        Continues_Num = findPreference("Continues_Num");

        Vibrator_Duration.setOnPreferenceChangeListener(this);
        Run_Delay_After.setOnPreferenceChangeListener(this);
        Continues_Num.setOnPreferenceChangeListener(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();

//        if (!("".equals(sp.getInt("Vibrator_Duration",3)))){
//            Vibrator_Duration.setSummary(sp.getInt("Vibrator_Duration",3));
//        }
//        if (!("".equals(sp.getInt("Run_Delay_After",1)))){
//            Run_Delay_After.setSummary(sp.getInt("Run_Delay_After",1));
//        }
//        if (!("".equals(sp.getInt("Continues_Num",3)))){
//            Continues_Num.setSummary(sp.getInt("Continues_Num",1));
//        }

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String key = preference.getKey();
        Log.i("cxl", "----------key---------" + key);
        switch (key) {
            case "Vibrator_Duration":
                if ("".equals(newValue.toString())) {
                    Log.i("cxl", newValue.toString());
                    Vibrator_Duration.setSummary("3");
                } else {
                    Vibrator_Duration.setSummary(newValue.toString());
                }
                edit.putInt("Vibrator_Duration", Integer.parseInt(Vibrator_Duration.getSummary().toString()));
                break;
            case "Run_Delay_After":
                if ("".equals(newValue.toString())) {
                    Log.i("cxl", newValue.toString());
                    Run_Delay_After.setSummary("1");
                } else {
                    Run_Delay_After.setSummary(newValue.toString());
                }

                edit.putInt("Run_Delay_After", Integer.parseInt(Run_Delay_After.getSummary().toString()));
                break;
            case "Continues_Num":
                if ("".equals(newValue.toString())) {
                    Log.i("cxl", newValue.toString());
                    Continues_Num.setSummary("1");
                } else {
                    Continues_Num.setSummary(newValue.toString());
                }
                edit.putInt("Continues_Num", Integer.parseInt(Continues_Num.getSummary().toString()));
                break;
        }
        edit.commit();

        /**
         * 返回值：true 代表将新值写入sharedPreference文件中
         *        false 则不将新值写入sharedPreference文件.
         *
         */
        return true;
    }
}
