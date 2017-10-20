package com.sagereal.streettest.test.vibrator;

import android.os.Vibrator;

import com.sagereal.streettest.TestAct;

/**
 * Created by gms on 17-10-19.
 */

public class VibratorTest extends TestAct {
    private static final String ACT_TITLE = "Vibrator Duration";
    private int mDelay = 0;
    private int mDuration = 500;
    private int mLoopTimes = 1;
    private Vibrator vibrator;

    public void setVibrator(Vibrator vibrator) {
        this.vibrator = vibrator;
    }

    public VibratorTest(int uid) {
        super(uid);
    }

    @Override
    public void execute() {
        int sec = 0;
        vibrate();

    }

    private void vibrate() {
        TestAct.SendProgressMsg(mUid, "s");
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mDuration);
            TestAct.SendProgressMsg(mUid, "Complete");
            TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", new Object[]{ACT_TITLE}));
        } else {
            TestAct.SendProgressMsg(mUid, "No vibrator!");
            TestAct.SendLogMsg(String.format("[TEST] %1$s: No vibrator!", new Object[]{ACT_TITLE}));
        }

    }

}


