package tobeclean.tobeclean;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;


import javax.inject.Inject;

import app.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import helpers.RuntimePermissionHelper;
import map.mvp.MapFragment;
import storage.Preferences;


public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    private static final String ENGLISH = "ENGLISH";
    private static final String HEBREW = "HEBREW";


    private LocationManager locationManager;
    private LocationListener locationListener;

    private RuntimePermissionHelper permissionHelper;
    private MapFragment mMapFragment = new MapFragment();

    //vars
    private GoogleMap mMap;
    public Boolean mLocationPermissionsGranted = false;



    boolean isPortScreen = true;
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


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        setSupportActionBar(toolbar);

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


        //permissionHelper = new RuntimePermissionHelper(this);

        if (isPortScreen) {

        }

    }


    private void init() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, mMapFragment)
                .commit();
    }


    /*This method create dialog. Tamir 19/03/18*/
    protected void startLanguageDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("CHANGE LANGUAGE");
        setTitleColor(Color.BLUE);

        dialog.setMessage("Choose your language");
        //dialog.setIcon();
        dialog.setPositiveButton(ENGLISH, dialogClickListener);
        dialog.setNegativeButton(HEBREW, dialogClickListener);

        dialog.create();
        dialog.show();
    }

    /*This interface set up buttons in dialog. Tamir 19/03/18  */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()

    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch (i) {

                //English
                case Dialog.BUTTON_POSITIVE: {
                    //Preferences.setLanguageApp( ENGLISH);
                    Toast.makeText(MainActivity.this, "Language app was changed to " + ENGLISH, Toast.LENGTH_SHORT).show();
                    break;
                }

                //Hebrew
                case Dialog.BUTTON_NEGATIVE: {
                    // Preferences.setLanguageApp(getApplicationContext(), HEBREW);
                    Toast.makeText(MainActivity.this, "Language app was changed to " + HEBREW, Toast.LENGTH_SHORT).show();
                    break;
                }

            }
        }
    };

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: in");

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i : grantResults) {

            if (i == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                Log.d(TAG, "onRequestPermissionsResult:  mLocationPermissionsGranted = true");
            } else {
                runtimePermissionHelper.requestPermissionsIfDenied(Manifest.permission.ACCESS_FINE_LOCATION);
            }

        }
    }
}
