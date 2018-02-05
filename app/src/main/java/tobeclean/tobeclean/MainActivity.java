package tobeclean.tobeclean;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import helpers.PermissionHelper;
import pages.MapCleanFragment;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_FINE_LOCATION =1 ;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private PermissionHelper permissionHelper;
    private MapCleanFragment mMapFragment = new MapCleanFragment();

    boolean isPortScreen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permissionHelper = new PermissionHelper(this);
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


       if(isPortScreen){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, mMapFragment)
                    .commit();
       }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            if (permissionHelper.getLocationPermission()) {
                mMapFragment.locationUpdateRequest();
            }
        }
    }
}
