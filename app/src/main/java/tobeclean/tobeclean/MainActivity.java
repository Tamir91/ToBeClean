package tobeclean.tobeclean;

import android.Manifest;
import android.app.Dialog;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import javax.inject.Inject;

import app.App;
import butterknife.ButterKnife;
import helpers.RuntimePermissionHelper;
import map.mvp.MapFragment;
import storage.Preferences;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //views
    private MapFragment mMapFragment = new MapFragment();

    //vars

    private RuntimePermissionHelper runtimePermissionHelper;

    @Inject
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //DI Dagger
        App.getApp(this).getAppComponent().injectMain(this);

        setSupportActionBar(toolbar);

        checkAllAppPermissions();
    }

    private void init() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, mMapFragment)
                .commit();
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