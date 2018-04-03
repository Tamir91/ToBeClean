package helpers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;

import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/02/18.
 */

public class RuntimePermissionHelper {

    private static RuntimePermissionHelper runtimePermissionHelper;
    public static final int PERMISSION_REQUEST_CODE = 1;
    private Activity activity;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private ArrayList<String> requiredPermissions;
    private ArrayList<String> ungrantedPermissions = new ArrayList<>();

    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    public RuntimePermissionHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return RuntimePermissionHelper
     */
    public static synchronized RuntimePermissionHelper getInstance(Activity activity) {
        if (runtimePermissionHelper == null) {
            runtimePermissionHelper = new RuntimePermissionHelper(activity);
        }
        return runtimePermissionHelper;
    }

    private void initPermissions() {
        requiredPermissions = new ArrayList<>();
        requiredPermissions.add(PERMISSION_ACCESS_FINE_LOCATION);
        //Add all the required permission in the list
    }


    public void requestPermissionsIfDenied() {
        ungrantedPermissions = getUnGrantedPermissionsList();
        if (canShowPermissionRationaleDialog()) {
            showMessageOKCancel(activity.getResources().getString(R.string.permission_message),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            askPermissions();
                        }
                    });
            return;
        }
        askPermissions();
    }

    public void requestPermissionsIfDenied(final String permission){
        if(canShowPermissionRationaleDialog(permission)){
            showMessageOKCancel(activity.getResources().getString(R.string.permission_message),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            askPermission(permission);
                        }
                    });
            return;
        }
        askPermission(permission);
    }

    public boolean canShowPermissionRationaleDialog() {
        boolean shouldShowRationale = false;
        for (String permission : ungrantedPermissions) {
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if (shouldShow) {
                shouldShowRationale = true;
            }
        }
        return shouldShowRationale;
    }

    public boolean canShowPermissionRationaleDialog(String permission) {
        boolean shouldShowRationale = false;
        boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        if (shouldShow) {
            shouldShowRationale = true;
        }
        return shouldShowRationale;
    }

    private void askPermissions() {
        if (ungrantedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(activity, ungrantedPermissions.toArray(new String[ungrantedPermissions.size()]), PERMISSION_REQUEST_CODE);
        }
    }

    private void askPermission(String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(activity, ErrorActivity.class);
                        intent.putExtra("permissions_denied", true);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .create()
                .show();
    }

    public boolean isAllPermissionAvailable() {
        boolean isAllPermissionAvailable = true;
        initPermissions();
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                isAllPermissionAvailable = false;
                break;
            }
        }
        return isAllPermissionAvailable;
    }

    public ArrayList<String> getUnGrantedPermissionsList() {
        ArrayList<String> list = new ArrayList<>();
        for (String permission : requiredPermissions) {
            int result = ActivityCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }
        return list;
    }

    public boolean isPermissionAvailable(String permission) {
        boolean isPermissionAvailable = true;
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            isPermissionAvailable = false;
        }
        return isPermissionAvailable;
    }

    private class ErrorActivity {
    }



    /*TODO comment*//*
    public boolean getLocationPermissionOld() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
            locationPermissionGranted = true;
        }
        return false;
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                //initMap();
            } else {
                ActivityCompat.requestPermissions(activity,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }*/
}
