package tobeclean.tobeclean;

import android.Manifest;
import android.app.Dialog;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


import javax.inject.Inject;

import butterknife.ButterKnife;
import helpers.RuntimePermissionHelper;
import storage.Preferences;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private RuntimePermissionHelper runtimePermissionHelper;

    @Inject
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        checkAllAppPermissions();
    }

    private void init() {

        switch (getWindowManager().getDefaultDisplay().getRotation()) {

            case Surface.ROTATION_0: {
                Log.d(TAG, "init::SCREEN_ORIENTATION_PORTRAIT");

                startFragment(R.id.fragPortraitContainer, mapFragment);
                break;
            }

            case Surface.ROTATION_90:
            case Surface.ROTATION_270: {
                Log.d(TAG, "init::SCREEN_ORIENTATION_LANDSCAPE");

                startFragment(R.id.fragPlacesContainer, placesFragment);
                startFragment(R.id.fragMapContainer, mapFragment);
                break;
            }

            case Surface.ROTATION_180: {
                Log.d(TAG, "init::SCREEN_ORIENTATION_180");
                break;
            }

            default: {
                Log.d(TAG, "init::Unknown screen orientation");
            }
        }
    }

    //This function did problem on Meizu.
    private int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    Log.e(TAG, "Unknown screen orientation. Defaulting to " +
                            "portrait.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    Log.e(TAG, "Unknown screen orientation. Defaulting to " +
                            "landscape.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    public boolean isServiceOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void checkAllAppPermissions() {
        if (isServiceOK()) {

            if (Build.VERSION.SDK_INT >= 23) {

                runtimePermissionHelper = RuntimePermissionHelper.getInstance(this);

                if (runtimePermissionHelper.isAllPermissionAvailable()) {

                    init();
                    // All permissions available. Go with the flow

                } else {

                    // Few permissions not granted. Ask for ungranted permissions
                    runtimePermissionHelper.setActivity(this);
                    runtimePermissionHelper.requestPermissionsIfDenied();

                }

            } else {

                init();
                // SDK below API 23. Do nothing just go with the flow.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: in");

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i : grantResults) {

            if (i == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult::mLocationPermissionsGranted = true");
            } else {
                runtimePermissionHelper.requestPermissionsIfDenied(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }
}