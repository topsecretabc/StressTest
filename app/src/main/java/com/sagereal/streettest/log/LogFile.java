package com.sagereal.streettest.log;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.sagereal.streettest.util.IntSdCardUtil;
import com.sagereal.streettest.util.SysUtil;

import java.io.File;

public class LogFile {
    private static final String NEW_LINE = "\r\n";
    protected File mLogFile;

    public LogFile() {
        this.mLogFile = null;
        this.mLogFile = null;
    }

    public LogFile(Context context, String savePath, String fileName) {
        this.mLogFile = null;
        this.mLogFile = createFile(savePath, fileName);
        if (this.mLogFile != null) {
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + savePath + "/"))));
        }
    }

    public void write(String content) {
        if (IntSdCardUtil.isMounted() && this.mLogFile != null) {
            LogManager.getPlatform().getFileIO().append(this.mLogFile, content);
        }
    }

    public void writeLine(String content) {
        write(SysUtil.getTimeStampString() + ": " + content + NEW_LINE);
    }

    private File createFile(String savePath, String fileName) {
        File logDir = IntSdCardUtil.createDir(savePath);
        if (logDir != null) {
            return new File(logDir, fileName);
        }
        return null;
    }
}
