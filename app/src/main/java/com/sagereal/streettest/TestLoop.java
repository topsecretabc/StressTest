package com.sagereal.streettest;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gms on 17-10-12.
 */

public class TestLoop extends Thread {
    private List<TestAct> mTestActList;
    private TestAct mCurrAct;
    private boolean isRunning;
    private static Handler handler;


    public TestLoop() {
        isRunning = true;
        mTestActList = new ArrayList<>();
    }

    public void add(TestAct act) {
        mTestActList.add(act);
    }

    @Override
    public void run() {

        for (TestAct act : mTestActList) {
            mCurrAct = act;
            act.execute();
            if (isRunning == false) break;
        }
        TestAct.SendEndMsg();


    }

    @Override
    public void interrupt() {
        isRunning = false;
        mCurrAct.stop();
        super.interrupt();
    }


}
