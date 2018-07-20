package com.sagereal.streettest.log;

import android.content.Context;

import com.sagereal.streettest.util.IntSdCardUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestRun {
    private static final String RESULT_ROOT_DIR = "StressTestLog/";
    private String mId;
    private File mResultPath;

    public TestRun() {
        this.mId = null;
        this.mResultPath = null;
        this.mId = generateId();
        this.mResultPath = createResultPath();
    }

    public TestRun(String id) {
        this.mId = null;
        this.mResultPath = null;
        this.mId = id;
        this.mResultPath = createResultPath();
    }

    public String getId() {
        return this.mId;
    }

    public void setup(Context context) {
        createResultPath();
        saveSetting(context);
    }

    public String getResultDir() {
        return RESULT_ROOT_DIR + this.mId;
    }

    private static String generateId() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    private File createResultPath() {
        return IntSdCardUtil.createDir(getResultDir());
    }

    private void saveSetting(Context context) {
        //SettingManager.writeToFile(context, Uri.fromFile(new File(this.mResultPath, SettingManager.DEFAULT_FILENAME)));
    }
}
