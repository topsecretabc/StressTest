package com.sagereal.streettest.test.camera;

import android.view.SurfaceView;

public class FrontCameraCaptureTest extends CameraCaptureTest {
    public FrontCameraCaptureTest(int uid, SurfaceView surfaceView, String runId) {
        super(uid, surfaceView, runId, 1);
    }

    protected void doTakePicture() {
        this.mCamera.takePicture(null, null, this.jpegCallback);
    }
}
