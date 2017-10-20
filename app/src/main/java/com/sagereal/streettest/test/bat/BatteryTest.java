package com.sagereal.streettest.test.bat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import com.sagereal.streettest.TestAct;

/**
 * Created by gms on 17-10-19.
 */

public class BatteryTest extends TestAct {
    protected static final String ACT_TITLE = "Battery Info";
    private String[] mBatt = new String[10];
    private Activity activity;
    private int mTimeout;
    private boolean isOver;

    public BatteryTest(int uid) {
        super(uid);
        mTimeout = 10;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void execute() {
        SendProgressMsg(mUid, "Start");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        activity.registerReceiver(mBatteryReceiver, filter);
        int sec = 0;
        try {

            while (sec < mTimeout) {
                if (isOver) break;
                sec++;
                Thread.sleep(1000);
            }
            //
            activity.unregisterReceiver(mBatteryReceiver);
            SendProgressMsg(mUid, "Complete");
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "status", mBatt[0]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "health", mBatt[1]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "present", mBatt[2]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "level", mBatt[3]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "scale", mBatt[4]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "icon-small", mBatt[5]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "plugged", mBatt[6]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "voltage", mBatt[7]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "temperature", mBatt[8]}));
            SendLogMsg(String.format("[TEST]%s:%s:%s", new Object[]{ACT_TITLE, "technology", mBatt[9]}));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("cxl", "action:" + action);
            /**
             * intent.ACTION_BATTERY_CHANGED:监听电池变化的广播数据
             */
            //if (action.equals(Intent.ACTION_BATTERY_CHANGED)){
            int status = intent.getIntExtra("status", 0);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN://未知
                    mBatt[0] = "unknown";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING://充电状态
                    mBatt[0] = "charging";

                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING://放电中
                    mBatt[0] = "discharging";

                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING://未充电
                    mBatt[0] = "not charging";

                    break;
                case BatteryManager.BATTERY_STATUS_FULL://电池满
                    mBatt[0] = "full";

                    break;

            }
            int health = intent.getIntExtra("health", 0);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN://未知
                    mBatt[1] = "unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD://正常
                    mBatt[1] = "good";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT://过热
                    mBatt[1] = "overheat";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD://没电
                    mBatt[1] = "dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE://过电压
                    mBatt[1] = "over voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE://未知错误
                    mBatt[1] = "unspecified failure";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD://电量低
                    mBatt[1] = "unspecified failure";
                    break;
            }
            mBatt[2] = String.valueOf(intent.getBooleanExtra("present",
                    false));//电池是否存在
            mBatt[3] = String.valueOf(intent.getIntExtra("level", 0));//现在电量数
            mBatt[4] = String.valueOf(intent.getIntExtra("scale", 0));//电池最大容量
            mBatt[5] = String.valueOf(intent.getIntExtra("icon-small",
                    0));

            int plugged = intent.getIntExtra("plugged", 0);
            if (mBatt[0].equals("charging")) {
                switch (plugged) {
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        mBatt[6] = "plugged ac";//充电器
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB://usb充电
                        mBatt[6] = "plugged usb";
                        Log.i("cxl", "------------plugged usb-----------");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_WIRELESS://无线设备充电
                        mBatt[6] = "plugged wireless";
                        break;
                }
            } else {
                mBatt[6] = "Unknown";//充电设备不明
            }
            mBatt[7] = String.valueOf(intent.getIntExtra("voltage", 0));//当前电池的电压
            double temp = (double) intent.getIntExtra("temperature", 0);//当前电池的温度

            mBatt[8] = String.valueOf(temp / 10.0);
            /**
             * 电池使用的技术。
             *           Li-ion(锂离子电池)
             *           Li-poly（聚合物电池）
             */
            String technology = intent.getStringExtra("technology");
            mBatt[9]=technology;
            isOver = true;
        }
    };


}
