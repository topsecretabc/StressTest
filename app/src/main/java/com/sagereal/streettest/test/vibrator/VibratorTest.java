package com.sagereal.streettest.test.vibrator;

import android.os.Vibrator;

import com.sagereal.streettest.TestAct;

/**
 * Created by chenxuelian on 17-09-25.
 */

public class VibratorTest extends TestAct {
    private static final String ACT_TITLE = "Vibrator Duration";
    private int mDuration = 1;
    private Vibrator vibrator;

    public void setVibrator(Vibrator vibrator) {
        this.vibrator = vibrator;
    }

    public VibratorTest(int uid) {
        super(uid);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    public void execute() {
        vibrate();

    }

    private void vibrate() {
        TestAct.SendProgressMsg(mUid, "start");
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mDuration * 1000);
            TestAct.SendProgressMsg(mUid, "Complete");
            TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", new Object[]{ACT_TITLE}));
        } else {
            TestAct.SendProgressMsg(mUid, "No vibrator!");
            TestAct.SendLogMsg(String.format("[TEST] %1$s: No vibrator!", new Object[]{ACT_TITLE}));
        }

    }

}


