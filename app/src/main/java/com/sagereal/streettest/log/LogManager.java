package com.sagereal.streettest.log;

public class LogManager {
    private static CLPlatform runningPlatform = null;

    public static CLPlatform getPlatform() {
        if (runningPlatform == null) {
            synchronized (LogManager.class) {
                if (runningPlatform == null) {
                    createRunningPlatform();
                }
            }
        }
        return runningPlatform;
    }

    private LogManager() {
    }

    private static void createRunningPlatform() {
        runningPlatform = new CLPlatform();
        runningPlatform.setFileIO(new FileIO());

    }

}
