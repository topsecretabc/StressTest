package com.sagereal.streettest.test.wifi;

import android.net.wifi.WifiManager;

import com.sagereal.streettest.TestAct;

public class NetOnOffTest extends NetTest {
    private static final String ACT_TITLE = "Network On/Off";
    private static final int ONOFF_DELAY = 10;
    private static final int ONOFF_TIMEOUT = 5000;
    private int mOffDelay = 5;
    private int mOnDelay = 5;
    private WifiManager mWifi = null;

    public NetOnOffTest(int uid) {
        super(uid);
    }

    public void setWifiManager(WifiManager manager) {
        this.mWifi = manager;
    }

    public void setOffDelay(int delay) {
        this.mOffDelay = delay;
    }

    public void setOnDelay(int delay) {
        this.mOnDelay = delay;
    }

    public void execute() {
        String desc = "";
        String logDesc = "";
        try {
            desc = "Turning Off Wi-Fi";
            SendProgressMsg(this.mUid, new StringBuilder(String.valueOf(desc)).append(" ...").toString());
            if (turnOffWifi()) {
                Thread.sleep((long) (this.mOffDelay * 1000));
                desc = "Turning On Wi-Fi";
                SendProgressMsg(this.mUid, new StringBuilder(String.valueOf(desc)).append(" ...").toString());
                if (turnOnWifi()) {
                    Thread.sleep((long) (this.mOnDelay * 1000));
                    SendProgressMsg(this.mUid, "Complete");
                    TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", new Object[]{ACT_TITLE}));
                    return;
                }
                logDesc = "[TEST] %1$s: " + desc + " Failed!";
                SendProgressMsg(this.mUid, "On Failed!!");
                TestAct.SendLogMsg(String.format(logDesc, new Object[]{ACT_TITLE}));
                return;
            }
            logDesc = "[TEST] %1$s: " + desc + " Failed!";
            SendProgressMsg(this.mUid, "Off Failed!!");
            TestAct.SendLogMsg(String.format(logDesc, new Object[]{ACT_TITLE}));
        } catch (InterruptedException e) {
        } catch (Exception ex) {
            TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, ex.getMessage()}));
            SendProgressMsg(this.mUid, "Failed!");
            ex.printStackTrace();
        }
    }

    private boolean turnOffWifi() throws IllegalArgumentException, InterruptedException {
        try {
            long start = System.currentTimeMillis();
            while (!this.mWifi.setWifiEnabled(false)) {
                if (System.currentTimeMillis() - start > 5000) {
                    return false;
                }
                Thread.sleep(10);
            }
            while (this.mWifi.getWifiState() != WifiManager.WIFI_STATE_DISABLED) {
                if (System.currentTimeMillis() - start > 5000) {
                    return false;
                }
                Thread.sleep(10);
            }
            return true;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (InterruptedException ex2) {
            throw ex2;
        }
    }

    private boolean turnOnWifi() throws IllegalArgumentException, InterruptedException {
        try {
            long start = System.currentTimeMillis();
            while (!this.mWifi.setWifiEnabled(true)) {
                if (System.currentTimeMillis() - start > 5000) {
                    return false;
                }
                Thread.sleep(10);
            }
            while (this.mWifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
                if (System.currentTimeMillis() - start > 5000) {
                    return false;
                }
                Thread.sleep(10);
            }
            return true;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (InterruptedException ex2) {
            throw ex2;
        }
    }
}
