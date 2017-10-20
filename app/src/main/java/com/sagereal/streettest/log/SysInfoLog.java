package com.sagereal.streettest.log;

import android.content.Context;

public class SysInfoLog extends LogFile {
    private static final String FILE_NAME = "licong.txt";
    private static final String SAVE_PATH = "LicongLog";
    private static SysInfoLog mLogFile = null;

    public SysInfoLog(Context context, String fileName) {
        super(context, SAVE_PATH, fileName);
    }

    public static SysInfoLog getInstance(Context context) {
        if (mLogFile != null) {
            return mLogFile;
        }
        return new SysInfoLog(context, FILE_NAME);
    }
}
