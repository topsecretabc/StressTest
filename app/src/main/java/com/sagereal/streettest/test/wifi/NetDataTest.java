package com.sagereal.streettest.test.wifi;

import com.sagereal.streettest.TestAct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class NetDataTest extends NetTest {
    private static final String ACT_TITLE = "Wifi Transmission";
    private String mAddr = "";
    private int mPort = 1024;
    private int mRepeatTimes = 8;

    public NetDataTest(int uid) {
        super(uid);
    }

    public void setAddr(String addr) {
        this.mAddr = addr;
    }

    public void setPort(int port) {
        this.mPort = port;
    }

    public void setRepeatTimes(int times) {
        this.mRepeatTimes = times;
    }

    public void execute() {
        InterruptedException ex;
        Exception ex2;
        String desc = "Sending data ....";
        Socket socket = null;
        SendProgressMsg(this.mUid, "Start");

        try {
            Socket socket2 = new Socket(InetAddress.getByName(this.mAddr), this.mPort);
            try {
                int i;
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
                String raw = "12345678";
                String msg = "";
                for (i = 0; i < 16; i++) {
                    msg = new StringBuilder(String.valueOf(msg)).append(raw).toString();
                }


                msg = new StringBuilder(String.valueOf(msg)).append("\r").toString();
                SendProgressMsg(this.mUid, desc);
                for (i = 0; i < this.mRepeatTimes; i++) {
                    out.println(msg);
                    Thread.sleep(100);
                    br.readLine();
                    Thread.sleep(100);
                    SendProgressMsg(this.mUid, desc);
                }
                SendProgressMsg(this.mUid, "Complete");
                TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", new Object[]{ACT_TITLE}));
                socket = socket2;
            } catch (InterruptedException e) {
                ex = e;
                socket = socket2;
            } catch (Exception e2) {
                ex2 = e2;
                socket = socket2;
            }
        } catch (Exception e4) {
            ex2 = e4;
            TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, ex2.getMessage()}));
            SendProgressMsg(this.mUid, "Failed!");
            ex2.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

