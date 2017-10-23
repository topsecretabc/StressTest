package com.sagereal.streettest.test.camera;

import android.view.SurfaceView;

/**
 * Created by duxinyun on 17-09-25.
 */


public class FrontCameraCaptureTest extends CameraCaptureTest {
    public FrontCameraCaptureTest(int uid, SurfaceView surfaceView, String runId) {
        super(uid, surfaceView, runId, 1);
    }

    protected void doTakePicture() {
        this.mCamera.takePicture(null, null, this.jpegCallback);
    }
}
