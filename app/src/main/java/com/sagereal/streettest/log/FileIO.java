package com.sagereal.streettest.log;

import java.io.File;
import java.io.RandomAccessFile;

public class FileIO {
    public void append(File targetFile, String content) {
        try {
            RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
            raf.seek(targetFile.length());
            raf.writeBytes(content);
            raf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
