package com.sagereal.streettest.test.wifi;

import java.io.IOException;

public class NetConnTest extends NetTest {
    private static final String ACT_TITLE = "Network Connection";
    private String mAddr = "127.0.0.1";
    private int mTimeout = 3;

    public NetConnTest(int uid) {
        super(uid);
    }

    public void setAddr(String addr) {
        this.mAddr = addr;
    }

    public void setTimeout(int timeout) {
        this.mTimeout = timeout;
    }

    public void execute() {
        String desc = "";

        try {

            SendProgressMsg(this.mUid, "Start");
            SendProgressMsg(this.mUid, "Ping " + this.mAddr);
            if (doPing(this.mAddr, this.mTimeout).booleanValue()) {
                desc = "Complete";
//                TestAct.SendLogMsg(String.format("[TEST] %1$s: OK", new Object[]{ACT_TITLE}));
            } else {
                desc = "Timeout";
//                TestAct.SendLogMsg(String.format("[TEST] %1$s: Timeout", new Object[]{ACT_TITLE}));
            }
            SendProgressMsg(this.mUid, desc);
        } catch (InterruptedException e) {
        } catch (Exception ex) {
//            TestAct.SendLogMsg(String.format("[TEST] %1$s: Exception(%2$s)", new Object[]{ACT_TITLE, ex.getMessage()}));
            SendProgressMsg(this.mUid, "Failed!");
            ex.printStackTrace();
        }
    }

    private Boolean doPing(String addr, int timeout) throws InterruptedException, IOException {
        try {
            if (Runtime.getRuntime().exec("ping -c 1 -w " + timeout + " " + addr).waitFor() == 0) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (InterruptedException ex) {
            throw ex;
        } catch (IOException ex2) {
            throw ex2;
        }
    }
}
