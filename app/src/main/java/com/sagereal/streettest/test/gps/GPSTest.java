package com.sagereal.streettest.test.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.sagereal.streettest.TestAct;

import java.util.Iterator;

/**
 * Created by duxinyun on 17-09-25.
 */


public class GPSTest extends TestAct {
    protected static final String ACT_TITLE = "GPS Capture";
    private final String TAG = "GPSTest";
    private LocationManager mLocationManager = null;
    private Location location = null;
    private int mTimeout;
    private Activity mActivity;
    private boolean isOver;

    public GPSTest(int uid) {
        super(uid);
        mTimeout = 5;
        isOver = false;
    }

    public void setMainActivity(Activity activity) {
        mActivity = activity;
        setListener();
    }

    @Override
    public void execute() {
        SendProgressMsg(mUid, "Start");
        int sec = 0;
        openGPS();
        try {

            while (sec < mTimeout) {
                if (isOver) break;
                sec++;
                Thread.sleep(1000);
            }
            if (location == null) {
                SendProgressMsg(mUid, "Failed");
                SendLogMsg(String.format("[TEST] %1$s: Failed", ACT_TITLE));
            } else {
                SendProgressMsg(mUid, "Complete");
                SendLogMsg(String.format("[TEST] %1$s: lComplete", ACT_TITLE));
            }

            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mLocationManager.removeUpdates(locationListener);
            mLocationManager.removeGpsStatusListener(statusListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openGPS() {
        boolean bGPS = isGPSEnable();
        if (!bGPS) {
            Intent GPS_intent = new Intent();
            GPS_intent
                    .setAction("android.settings.LOCATION_SOURCE_SETTINGS");
            GPS_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivityForResult(GPS_intent, 0);
        }
    }

    private void setListener() {
        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);
        mLocationManager.addGpsStatusListener(statusListener);

        location = mLocationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        updateWithNewLocation(location);
        updateGpsStatus(0, null);
    }

    // GPS Status Listener
    private final GpsStatus.Listener statusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            //LocationProvider.OUT_OF_SERVICE
            Log.i(TAG, "now gps status changed,the event is: " + event);
            if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            GpsStatus status = mLocationManager.getGpsStatus(null);
            updateGpsStatus(event, status);
        }
    };

    // Location listener
    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            Log.i(TAG, "now location changed");
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            Log.i(TAG, "now provider disable");
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
            Log.i(TAG, "now provider enable");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "now provider status changed" + status);
        }
    };

    // Check if the GPS is the location provider
    private boolean isGPSEnable() {

        String str = Settings.Secure.getString(mActivity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.i(TAG, "str of LOCATION_PROVIDERS_ALLOWED is:" + str);
        if (str != null) {

            return str.contains("gps");
        } else {
            return false;
        }
    }

    // Update UI when location and provider changed
    private void updateWithNewLocation(Location loc) {
        location = loc;
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            // Toast.makeText(mActivity, "位置:"+lat +"--------"+ lng, Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "location:" + lat + "--------" + lng);
            SendLogMsg(String.format("[TEST] %1$s: location(%s,%s)", new Object[]{ACT_TITLE, lat, lng}));

            isOver = true;
        } else {


        }

    }

    // Update Satellites count when status is changed
    private void updateGpsStatus(int event, GpsStatus status) {

        if (status == null) {
            //   mslView.setText("waiting...");
        } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                GpsSatellite s = it.next();
                count++;
            }
            //   mslView.setText(String.valueOf(count));
            //  Toast.makeText(mActivity, String.valueOf(count), Toast.LENGTH_SHORT).show();
        }
    }

}
