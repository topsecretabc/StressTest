package com.sagereal.streettest.test.camera;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.view.SurfaceView;

/**
 * Created by duxinyun on 17-09-25.
 */

public class BackCameraCaptureTest extends CameraCaptureTest {
    private AutoFocusCallback autoFocusCallback;
    private boolean mIsAutoFocus = false;

    public BackCameraCaptureTest(int uid, SurfaceView surfaceView, String runId) {
        super(uid, surfaceView, runId, 0);
        initParameter();
    }

    public void setAutoFocus(boolean focus) {
        this.mIsAutoFocus = focus;
    }

    protected void setupCamera() {
        super.setupCamera();
        if (this.mIsAutoFocus) {
            Parameters parameters = this.mCamera.getParameters();
            parameters.setFocusMode("auto");
            this.mCamera.setParameters(parameters);
        }
    }

    protected void doTakePicture() {
        if (this.mIsAutoFocus) {
            this.mCamera.autoFocus(this.autoFocusCallback);
        } else {
            this.mCamera.takePicture(null, null, this.jpegCallback);
        }
    }

    private void initParameter() {
        this.autoFocusCallback = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.takePicture(null, null, BackCameraCaptureTest.this.jpegCallback);
                    return;
                }
                // TestAct.SendLogMsg(String.format("[TEST] %1$s: Failed to focus(%2$s)", new Object[]{"Camera Photo Capture", BackCameraCaptureTest.this.mCameraFacingString[BackCameraCaptureTest.this.mCurrentCameraFacing]}));
            }
        };
    }
}
