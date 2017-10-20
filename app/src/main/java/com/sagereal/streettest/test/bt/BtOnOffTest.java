package com.sagereal.streettest.test.bt;

import android.bluetooth.BluetoothAdapter;

import com.sagereal.streettest.TestAct;

public class BtOnOffTest extends BtTest {
    private static final String ACT_TITLE = "Bt On/Off";
    private int mOffDelay = 5;
    private int mOnDelay = 5;

    public BtOnOffTest(int uid) {
        super(uid);
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
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            try {
                SendProgressMsg(this.mUid, "Turning Off BT ...");
                boolean bOffRtn = adapter.disable();
                if (!bOffRtn) {
                    SendProgressMsg(this.mUid, "Off Fail!!");
                }
                Thread.sleep((long) (this.mOffDelay * 1000));
                SendProgressMsg(this.mUid, "Turning On BT ...");
                boolean bOnRtn = adapter.enable();
                if (!bOnRtn) {
                    SendProgressMsg(this.mUid, "On Fail!!");
                }
                Thread.sleep((long) (this.mOnDelay * 1000));
                if (!bOffRtn) {
                    desc = "Failed!";
                    logDesc = "[TEST] %1$s: Turning Off BT Fail!";
                } else if (bOnRtn) {
                    desc = "Complete!";
                    logDesc = "[TEST] %1$s: OK";
                } else {
                    desc = "Failed!";
                    logDesc = "[TEST] %1$s: Turning On BT Fail!";
                }
                SendProgressMsg(this.mUid, desc);
                TestAct.SendLogMsg(String.format(logDesc, new Object[]{ACT_TITLE}));
            } catch (InterruptedException e) {
            } catch (Exception ex) {
                TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, ex.getMessage()}));
                SendProgressMsg(this.mUid, "Failed!");
                ex.printStackTrace();
            }
        }
    }
}
