package helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by tamir on 05/02/18.
 */

public class PermissionHelper {
    private static final int PERMISSION_REQUEST_FINE_LOCATION =1 ;
    private static boolean locationPermissionGranted=false;
    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    /*TODO comment*/
    public  boolean getLocationPermission(){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted=true;
            return true;
        }
        else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_FINE_LOCATION);
            locationPermissionGranted=true;
        }
        return  false;
    }
}
