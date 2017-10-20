package com.sagereal.streettest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sagereal.streettest.log.TestRun;
import com.sagereal.streettest.log.TestRunLog;
import com.sagereal.streettest.test.bat.BatteryTest;
import com.sagereal.streettest.test.bt.BtDataTest;
import com.sagereal.streettest.test.bt.BtOnOffTest;
import com.sagereal.streettest.test.camera.BackCameraCaptureTest;
import com.sagereal.streettest.test.gps.GPSTest;
import com.sagereal.streettest.test.vibrator.VibratorTest;
import com.sagereal.streettest.test.wifi.NetConnTest;
import com.sagereal.streettest.test.wifi.NetDataTest;
import com.sagereal.streettest.test.wifi.NetOnOffTest;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private static final String TAG = "mainactivity";
    public static final String HANDLER_FIELD_DESC = "desc";
    public static final String HANDLER_FIELD_UID = "uid";
    public static final String PREF_BT_DATATEST_DEVICE_ADDR = "BT_DATATEST_DEVICE_ADDR";
    private static final String BT_DATATEST_DEVICE_ADDR_DEFAULT = "";
    public static final String PREF_BT_ONOFFTEST_TURNOFF_DELAY = "BT_ONOFFTEST_TURNOFF_DELAY";
    public static final String PREF_BT_ONOFFTEST_TURNON_DELAY = "BT_ONOFFTEST_TURNON_DELAY";
    private Button start, stop;
    private boolean isRunning = false;
    private TestLoop testLoop;
    private RelativeLayout relativeLayout;
    private int count = 0;
    private CheckBox checkBoxBt, checkBoxWifi, checkBoxGPS, checkBoxVibrate, checkBoxBat, checkBoxCam;
    private TextView resultBt, resultWifi, resultGPS, resultVibrate, resultBat, resultCam;
    private Spinner spinner;
    private int doAction;
    private TestRun testRun = null;
    public static TestRunLog testRunLog = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startTest();
                        }
                    }, 3000);

                    break;
                case 2:
                    endTest();
                    break;
                case 3:
                    HandleProgressMessage(msg.getData());
                    break;

                case 4:
                    LogRunInfo(msg.getData().getString(MainActivity.HANDLER_FIELD_DESC));
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (SettingPrefMain.getIsloop(this)) {
            testRun = new TestRun(SettingPrefMain.getRunid(this));
            testRun.setup(this);
            testRunLog = new TestRunLog(getApplicationContext(), testRun.getResultDir());
            getPrefs();
            isRunning = true;
            spinner.setSelection(1);
            hideUi();
            startTest();
        } else {
            // testRun = new TestRun();
            // testRun.setup(this);
        }

    }

    //
    private void initView() {

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        checkBoxBt = (CheckBox) findViewById(R.id.bluetooth);
        checkBoxWifi = (CheckBox) findViewById(R.id.wifi);
        checkBoxGPS = (CheckBox) findViewById(R.id.gps);
        checkBoxVibrate = (CheckBox) findViewById(R.id.vibrate);
        checkBoxBat = (CheckBox) findViewById(R.id.battery);
        checkBoxCam = (CheckBox) findViewById(R.id.camera);
        resultBt = (TextView) findViewById(R.id.result_bt);
        resultWifi = (TextView) findViewById(R.id.result_wifi);
        resultGPS = (TextView) findViewById(R.id.result_gps);
        resultVibrate = (TextView) findViewById(R.id.result_vibrate);
        resultBat = (TextView) findViewById(R.id.result_bat);
        resultCam = (TextView) findViewById(R.id.result_cam);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        spinner = (Spinner) findViewById(R.id.spinner);

        checkBoxBt.setOnCheckedChangeListener(this);
        checkBoxWifi.setOnCheckedChangeListener(this);
        checkBoxGPS.setOnCheckedChangeListener(this);
        checkBoxVibrate.setOnCheckedChangeListener(this);
        checkBoxBat.setOnCheckedChangeListener(this);
        checkBoxCam.setOnCheckedChangeListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }


    private void showUi() {
        start.setEnabled(true);
        stop.setEnabled(false);
        spinner.setEnabled(true);
        checkBoxBt.setEnabled(true);
        checkBoxWifi.setEnabled(true);
        checkBoxGPS.setEnabled(true);
        checkBoxVibrate.setEnabled(true);
        checkBoxBat.setEnabled(true);
        checkBoxCam.setEnabled(true);
    }

    private void hideUi() {
        doAction = spinner.getSelectedItemPosition();
        start.setEnabled(false);
        stop.setEnabled(true);
        spinner.setEnabled(false);
        checkBoxBt.setEnabled(false);
        checkBoxWifi.setEnabled(false);
        checkBoxGPS.setEnabled(false);
        checkBoxVibrate.setEnabled(false);
        checkBoxBat.setEnabled(false);
        checkBoxCam.setEnabled(false);
    }


    private void startTest() {
        if (!isRunning) return;
        setTitle(getResources().getString(R.string.app_name) + "(" + (count + 1) + ")");
        LogRunInfo("[START] Loop count:" + (count + 1));
        testLoop = new TestLoop();
        TestAct.setHandler(handler);


        if (checkBoxBt.isChecked()) {
            BtDataTest act3 = new BtDataTest(1);
            String string = SettingPref.getSettingSharedPreferences(this).getString(PREF_BT_DATATEST_DEVICE_ADDR, BT_DATATEST_DEVICE_ADDR_DEFAULT);
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            act3.setAddr(string);
            act3.setRetryTimes(5);
            this.testLoop.add(act3);
            BtOnOffTest act4 = new BtOnOffTest(1);
            act4.setOffDelay(Integer.parseInt(SettingPref.getSettingSharedPreferences(this).getString(PREF_BT_ONOFFTEST_TURNOFF_DELAY, Integer.toString(5))));
            act4.setOnDelay(Integer.parseInt(SettingPref.getSettingSharedPreferences(this).getString(PREF_BT_ONOFFTEST_TURNON_DELAY, Integer.toString(5))));
            this.testLoop.add(act4);
        }
        if (checkBoxWifi.isChecked()) {
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            ConnectivityManager mConnManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnManager.getActiveNetworkInfo();
            if (wifiManager != null
                    && wifiManager.getWifiState() == 3
                    && (networkInfo != null || networkInfo.getType() == 1)
                    && networkInfo.isConnected()) {
//            WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String IPAddress = intToIp(wifiInfo.getIpAddress());
                Log.e("qwer", IPAddress);
                DhcpInfo dhcpinfo = wifiManager.getDhcpInfo();
                String serverAddress = intToIp(dhcpinfo.serverAddress);
                NetConnTest act = new NetConnTest(2);
                act.setAddr("127.0.0.1");
                act.setTimeout(3);
                this.testLoop.add(act);
                NetDataTest act2 = new NetDataTest(2);
                act2.setAddr(IPAddress);
                act2.setPort(1024);
                act2.setRepeatTimes(8);
                this.testLoop.add(act2);
                NetOnOffTest act3 = new NetOnOffTest(2);
                act3.setWifiManager((WifiManager) getSystemService(Context.WIFI_SERVICE));
                act3.setOffDelay(5);
                act3.setOnDelay(5);
                this.testLoop.add(act3);
//            WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
//            if (!wifiManager.isWifiEnabled()) {
//                System.out.println("=================");
//                wifiManager.setWifiEnabled(true);
//            }
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            String IPAddress = intToIp(wifiInfo.getIpAddress());
//            System.out.println("IPAddress-->>" + IPAddress);
//
//            DhcpInfo dhcpinfo = wifiManager.getDhcpInfo();
//            String serverAddress = intToIp(dhcpinfo.serverAddress);
//            System.out.println("serverAddress-->>" + serverAddress);
//            其中IPAddress 是本机的IP地址，serverAddress 是你所连接的wifi热点对应的IP地址
            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();
            }
        }

        //
        if (checkBoxGPS.isChecked()) {
            GPSTest act = new GPSTest(3);
            act.setMainActivity(this);
            testLoop.add(act);
        }
        //


        if (checkBoxVibrate.isChecked()){
            VibratorTest act=new VibratorTest(4);
            act.setVibrator((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
            testLoop.add(act);
        }
        if (checkBoxBat.isChecked()) {
            BatteryTest act = new BatteryTest(5);
            act.setActivity(this);
            testLoop.add(act);
        }
        if (checkBoxCam.isChecked()) {
            BackCameraCaptureTest act1 = new BackCameraCaptureTest(6, new SurfaceView(MainActivity.this), "test");
            act1.setAutoFocus(true);
            act1.setMainActivity(MainActivity.this);
            act1.setRelativeLayout(relativeLayout);
            testLoop.add(act1);
            //
            /*FrontCameraCaptureTest act2 = new FrontCameraCaptureTest(6, new SurfaceView(MainActivity.this), "test");
            act2.setMainActivity(MainActivity.this);
            act2.setRelativeLayout(relativeLayout);
            testLoop.add(act2);*/
        }
        setText();
        testLoop.start();
    }

    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    private void endTest() {
        LogRunInfo("[END] Loop count:" + (count + 1) + "\n");
        if (!isRunning) return;
        if (testLoop != null && testLoop.isAlive())
            testLoop.interrupt();
        testLoop = null;
        if (++count < 3) {
            doActionComplate();
        } else {
            SettingPrefMain.setIsloop(this, false);
            count = 0;
            showUi();
            testRunLog = null;
        }
    }

    public void LogRunInfo(String data) {
        if (testRunLog != null) {
            testRunLog.writeLine(data);
        }
    }

    private void doActionComplate() {
        // AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (doAction == 0) {
            if (isRunning) handler.sendEmptyMessage(1);

        } else if (doAction == 1) {
            SettingPrefMain.setCount(this, count);
            SettingPrefMain.setIsloop(this, true);
            pm.reboot("FORCE Reboot");
        } else {
            if (isRunning) handler.sendEmptyMessage(1);
        }


    }


    private void stopTest() {
        if (testLoop != null && testLoop.isAlive()) {
            testLoop.interrupt();
        }
        testLoop = null;
        count = 0;
        SettingPrefMain.setIsloop(this, false);
        setText();
        setTitle(getResources().getString(R.string.app_name));
        testRunLog = null;

    }


    private void setText() {
        resultBt.setText("");
        resultWifi.setText("");
        resultGPS.setText("");
        resultVibrate.setText("");
        resultBat.setText("");
        resultCam.setText("");
    }

    private void HandleProgressMessage(Bundle data) {
        TextView txt;
        switch (data.getInt(HANDLER_FIELD_UID)) {
            case 1:
                txt = resultBt;
                break;
            case 2:
                txt = resultWifi;
                break;
            case 3:
                txt = resultGPS;
                break;
            case 4:
                txt = resultVibrate;
                break;
            case 5:
                txt = resultBat;
                break;
            case 6:
                txt = resultCam;
                break;
            default:
                return;
        }
        String desc = data.getString(HANDLER_FIELD_DESC, "");
        txt.setText(desc);
    }

    private void savePrefs() {
        SettingPrefMain.setBluetooth(this, checkBoxBt.isChecked());
        SettingPrefMain.setWifi(this, checkBoxWifi.isChecked());
        SettingPrefMain.setGps(this, checkBoxGPS.isChecked());
        SettingPrefMain.setVibrate(this, checkBoxVibrate.isChecked());
        SettingPrefMain.setBattery(this, checkBoxBat.isChecked());
        SettingPrefMain.setCamera(this, checkBoxCam.isChecked());
        SettingPrefMain.setRunid(this, testRun.getId());

    }

    private void getPrefs() {
        checkBoxBt.setChecked(SettingPrefMain.getBluetooth(this));
        checkBoxWifi.setChecked(SettingPrefMain.getWifi(this));
        checkBoxGPS.setChecked(SettingPrefMain.getGps(this));
        checkBoxVibrate.setChecked(SettingPrefMain.getVibrate(this));
        checkBoxBat.setChecked(SettingPrefMain.getBattery(this));
        checkBoxCam.setChecked(SettingPrefMain.getCamera(this));
        count = SettingPrefMain.getCount(this);
    }


    private void onCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断当前系统的SDK版本是否大于23
            //如果当前申请的权限没有授权
            if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    || !(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    || !(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                //请求权限
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {//已经授权了就走这条分支
                startTest();

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && permissions[1].equals(Manifest.permission.CAMERA)
                    && permissions[2].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                //得到权限之后去做的业务
                startTest();
            } else {//没有获得到权限
                Toast.makeText(this, "how to play if you not give me power !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkBoxBt.isChecked() || checkBoxGPS.isChecked()
                || checkBoxCam.isChecked() || checkBoxBat.isChecked()
                || checkBoxVibrate.isChecked() || checkBoxWifi.isChecked()) {
            start.setEnabled(true);
        } else {
            start.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                testRun = new TestRun();
                testRun.setup(this);
                testRunLog = new TestRunLog(getApplicationContext(), testRun.getResultDir());
                savePrefs();
                isRunning = true;
                hideUi();
                onCallPermission();
                break;
            case R.id.stop:
                isRunning = false;
                showUi();
                stopTest();
                break;
        }
    }
}
