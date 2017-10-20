package com.sagereal.streettest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class TestAct {
    private static Handler handler = null;
    protected boolean mIsRunning;
    protected int mUid = -1;

    public abstract void execute();

    public TestAct(int uid) {
        this.mUid = uid;
        this.mIsRunning = true;
    }

    public final void stop() {
        this.mIsRunning = false;
    }


    public static void setHandler(Handler h) {
        handler = h;
    }

    public static void SendProgressMsg(int HwId, String desc) {
        Bundle msgData = new Bundle();
        msgData.putInt(MainActivity.HANDLER_FIELD_UID, HwId);
        msgData.putString(MainActivity.HANDLER_FIELD_DESC, desc);
        Message msg = new Message();
        msg.setData(msgData);
        msg.what = 3;
        handler.sendMessage(msg);
    }

    public static void SendEndMsg() {
        handler.sendEmptyMessage(2);
    }

    public static void SendLogMsg(String desc) {
        if (MainActivity.testRunLog != null) {
            MainActivity.testRunLog.writeLine(desc);
        }
    }

}
