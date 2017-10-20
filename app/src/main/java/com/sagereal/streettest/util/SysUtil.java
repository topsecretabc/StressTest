package com.sagereal.streettest.util;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SysUtil {
    public static String getBuildNumber() {
        return Build.DISPLAY;
    }

    public static String getTimeStampString() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
    }
}
