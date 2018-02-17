package tobeclean.tobeclean;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;

import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import helpers.PermissionHelper;
import pages.MapCleanFragment;
//import pages.MapCleanFragment;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_FINE_LOCATION =1 ;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;



    private LocationManager locationManager;
    private LocationListener locationListener;
    private PermissionHelper permissionHelper;
    private MapCleanFragment mMapFragment = new MapCleanFragment();

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;

    boolean isPortScreen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(isServiceOK()){
            init();
        }


        //permissionHelper = new PermissionHelper(this);

       if(isPortScreen){

       }

    }

    private void init(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, mMapFragment)
                .commit();
    }

    public  boolean isServiceOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}
