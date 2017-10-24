package com.sagereal.streettest.test.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.sagereal.streettest.TestAct;
import com.sagereal.streettest.util.SysUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by licong on 17-09-25.
 */


public class BtDataTest extends BtTest {
    private static final String ACT_TITLE = "Bt Transmission";
    private String mAddr = "";
    private String mMac = "";
    private int mRetryTimes = 3;

    public BtDataTest(int uid) {
        super(uid);
    }

    public void setAddr(String addr) {
        this.mAddr = addr;
    }

    public void setRetryTimes(int times) {
        this.mRetryTimes = times;
    }

    public void execute() {
        boolean isTesting = true;
        int prog = 0;
        String desc = "";
        String errMsg = "";
        int steps = 0;
        int retry = 0;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        if (devices.size() > 0) {
            for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator.hasNext(); ) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                String name = bluetoothDevice.getName();
                if (name.equals(this.mAddr)) {
                    mMac = bluetoothDevice.getAddress();
                }
            }
        }

        if (adapter != null) {
            BluetoothSocket socket = null;
            while (this.mIsRunning && isTesting) {
                switch (steps) {
                    case 0:
                        prog = 0;
                        try {
                            SendProgressMsg(this.mUid, "Start");
                            socket = adapter.getRemoteDevice(this.mMac).createRfcommSocketToServiceRecord(this.SPP_UUID);
                            steps++;//1
                            retry = 0;
                            break;
                        } catch (Exception ex) {
                            errMsg = ex.getMessage();
                            ex.printStackTrace();
                            TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, errMsg}));
                            retry++;
                            break;
                        }
                    case 1:
                        prog = 25;
                        SendProgressMsg(this.mUid, "Connecting");
                        try {
                            socket.connect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        steps++;
                        retry = 0;
                        break;
                    case 2:
                        SendProgressMsg(this.mUid, "Sending data");
                        try {
                            socket.getOutputStream().write((SysUtil.getTimeStampString() + "\r").getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        steps++;
                        break;
                    case 3:
                        // prog = 100;
                        SendProgressMsg(this.mUid, "Data Complete");
                        TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", ACT_TITLE));
                        isTesting = false;
                        break;
                }
//                retry = 0;
                if (retry >= this.mRetryTimes) {
                    SendProgressMsg(this.mUid, "Failed");
                    TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, errMsg}));
                    isTesting = false;
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex2) {
                    TestAct.SendLogMsg(String.format("[TEST] %1$s: Close Socket Exception(%2$s)", new Object[]{ACT_TITLE, ex2.getMessage()}));
                    ex2.printStackTrace();
                }
            }
        }
    }
}


