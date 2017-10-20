package com.sagereal.streettest.test.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.sagereal.streettest.TestAct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class CameraCaptureTest extends CameraTest {
    protected static final String ACT_TITLE = "Camera Photo Capture";
    private static final String TAG = "cameracapturetest";
    private Callback SurfaceHolderCallback;
    protected PictureCallback jpegCallback;
    private Activity mActivity;
    private Bitmap mBitmap;
    protected Camera mCamera;
    protected final String[] mCameraFacingString;
    protected int mCurrentCameraFacing;
    private RelativeLayout mRelativeLayout;
    private String mSavePath;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private int mTimeout;

    protected abstract void doTakePicture();

    public CameraCaptureTest(int uid, SurfaceView surfaceView, String runId, int cameraFacing) {
        super(uid);
        this.mActivity = null;
        this.mRelativeLayout = null;
        this.mCameraFacingString = new String[]{"Back", "Front"};
        this.SurfaceHolderCallback = new Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                mCamera = openCamera(mCurrentCameraFacing);
                mCamera.setDisplayOrientation(90);
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                setupCamera();
                mCamera.startPreview();
                doTakePicture();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                holder.removeCallback(SurfaceHolderCallback);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        };
        jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] imgData, Camera camera) {
                if (imgData != null) {
                    Bitmap bitmap = rotationBitmap(BitmapFactory.decodeByteArray(imgData, 0, imgData.length));
                    if (saveBitmap(bitmap)) {
                        mBitmap = bitmap;
                    }
                    bitmap.recycle();
                }
            }
        };
        this.mBitmap = null;
        this.mTimeout = 10;
        this.mCurrentCameraFacing = cameraFacing;
        this.mSavePath = getSavePath(runId);
        this.mCamera = null;
        this.mSurfaceView = surfaceView;
        this.mSurfaceHolder = mSurfaceView.getHolder();
        this.mSurfaceHolder.addCallback(SurfaceHolderCallback);
    }

    public void setMainActivity(Activity activity) {
        mActivity = activity;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.mRelativeLayout = relativeLayout;
    }

    public void execute() {
        String desc = "";
        String logDesc = "";
        SendProgressMsg(mUid, "test");
        int sec = 0;
        try {
            ShowCameraView(true);
            while (sec < mTimeout) {
                if (mBitmap != null) {
                    break;
                }
                sec++;
                Thread.sleep(1000);
            }
            if (mBitmap != null) {
                desc = "Complete";
                logDesc = "[TEST] %1$s: OK(%2$s)";
                SendProgressMsg(mUid, "Complete");
                mBitmap.recycle();
                mBitmap = null;
                refreshSavePath();
            } else {
                desc = "Failed";
                logDesc = "[TEST] %1$s: Failed(%2$s)";

            }
            SendLogMsg(String.format(logDesc, new Object[]{ACT_TITLE, mCameraFacingString[mCurrentCameraFacing]}));
            SendProgressMsg(mUid, desc);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex2) {
            TestAct.SendLogMsg(String.format("[TEST] %1$s: Failed(%2$s), Exception(%2$s)", new Object[]{ACT_TITLE, mCameraFacingString[mCurrentCameraFacing], ex2.getMessage()}));
            ex2.printStackTrace();
        } finally {
            ShowCameraView(false);
        }
    }

    private void ShowCameraView(final boolean show) {
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (show) {
                    mRelativeLayout.addView(mSurfaceView);
                } else {
                    mRelativeLayout.removeView(mSurfaceView);
                }
            }
        });
    }

    private Camera openCamera(int CameraFacing) {
        Camera cam = null;
        CameraInfo cameraInfo = new CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == CameraFacing) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    TestAct.SendLogMsg(String.format("[TEST] %1$s: Failed to open(%2$s), Exception(%3$s)", new Object[]{ACT_TITLE, mCameraFacingString[CameraFacing], ex.getMessage()}));
                }
            }
        }
        return cam;
    }

    protected void setupCamera() {
        Parameters parameters = mCamera.getParameters();
        parameters.setAntibanding("60hz");
        parameters.setWhiteBalance("auto");
        mCamera.setParameters(parameters);
    }

    private Bitmap rotationBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) 90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    private boolean saveBitmap(Bitmap bitmap) {
        String time = "";
        File dir = new File(mSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            FileOutputStream fOut = new FileOutputStream(mSavePath + "/IMG_" + time + ".jpg");
            bitmap.compress(CompressFormat.JPEG, 80, fOut);
            fOut.flush();
            fOut.close();
            TestAct.SendLogMsg(String.format("[TEST] %1$s: Save OK(%2$s) IMG_%3$s.jpg", new Object[]{ACT_TITLE, mCameraFacingString[mCurrentCameraFacing], time}));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            TestAct.SendLogMsg(String.format("[TEST] %1$s: Save Failed(%2$s), Exception(%3$s)", new Object[]{ACT_TITLE, mCameraFacingString[mCurrentCameraFacing], ex.getMessage()}));
            return false;
        }
    }

    private String getSubPathByDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String getSavePath(String runId) {
        return Environment.getExternalStorageDirectory() + "/StressTest/DCIM/" + runId + "/" + mCameraFacingString[mCurrentCameraFacing] + "Camera/" + getSubPathByDate();
    }

    private void refreshSavePath() {
        Uri contentUri = Uri.fromFile(new File(mSavePath));
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(contentUri);
        mActivity.sendBroadcast(intent);
    }
}
