package com.sagereal.streettest.util;

import android.os.Environment;

import java.io.File;

public class IntSdCardUtil {
    public static boolean isMounted() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static File createDir(String path) {
        if (!isMounted()) {
            return null;
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory(), path);
            file.mkdirs();
            return file;
        } catch (Exception e) {
            return null;
        }
    }
}
