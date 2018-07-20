package com.sagereal.streettest.log;

import android.content.Context;

public class TestRunLog extends LogFile {
    private static final String FILE_NAME = "log.txt";

    public TestRunLog(Context context, String savePath) {
        super(context, savePath, FILE_NAME);
    }
}
