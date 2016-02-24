package com.sunay.moony.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sunay on 16-2-23.
 */
public class LocationUtils {
    static Context mContext;
    static String mCityName;
    private static List<Address> addresses;
    private static LocationUtils instance;
    private static Geocoder gcd;

    private LocationUtils(Context context) {
        mContext = context;
        gcd = new Geocoder(mContext, Locale.getDefault());
    }


    public static LocationUtils getInstance(Context context) {
        if (instance == null) {
            instance = new LocationUtils(context);
        }
        return instance;
    }


    public String getCity() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        Location locationGps = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationWifi = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        GPSTracker gpsTracker = new GPSTracker(mContext);

        if (!gpsTracker.getIsGPSTrackingEnabled()) {
            gpsTracker.showSettingsAlert();
        } else {
            try {
                if (locationGps != null && locationGps.getLongitude() != 0.0 &&
                    locationGps.getLatitude() != 0.0) {
                    addresses =
                        gcd.getFromLocation(locationGps.getLatitude(), locationGps.getLongitude(),
                            1);
                } else if (locationWifi != null && locationWifi.getLongitude() != 0.0 &&
                    locationWifi.getLatitude() != 0.0) {
                    addresses =
                        gcd.getFromLocation(locationWifi.getLatitude(), locationWifi.getLongitude(),
                            1);
                }
                mCityName = addresses.get(0)
                    .getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                try {
                    addresses =
                        gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    mCityName = addresses.get(0)
                        .getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        return mCityName;
    }
}
