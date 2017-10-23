package com.sagereal.streettest.test.bt;

import com.sagereal.streettest.TestAct;

import java.util.UUID;

/**
 * Created by licong on 17-09-25.
 */

public abstract class BtTest extends TestAct {
    //00001101-0000-1000-8000-00805F9B34FB 可以得到蓝牙串口服务
    protected UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BtTest(int uid) {
        super(uid);
    }
}
