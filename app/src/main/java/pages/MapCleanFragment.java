package pages;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import tobeclean.tobeclean.R;


/**
 * Created by tamir on 05/02/18.
 */

public class MapCleanFragment extends Fragment {


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private SupportMapFragment mapFragment;

    Context context;
    View view;

    private static final String TAG = "MapActivity";

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment_activity, container, false);
        context = getActivity();
        getLocationPermission();

        initViews();

        //mapFragment.getMapAsync();
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                    //setMapMarker(); //This line only for test

                }
            });
        } else {
            Toast.makeText(context, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    /*Loading Google map*/
    protected void loadMap(GoogleMap googleMap) {
        Log.d(TAG, "initMap: initializing map");
        mMap = googleMap;
        if (mMap != null) {
            // Map is ready
            Toast.makeText(context, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
          /*  MapDemoActivityPermissionsDispatcher.getMyLocationWithPermissionCheck(this);
            MapDemoActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);*/
        } else {
            Toast.makeText(context, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    //Todo add permissions for SQLite writing and reading
    /*This method getLocation permissions*/
    private boolean getLocationPermission() {

        Log.d(TAG, "getLocationPermission: getting location permissions");
        int locationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    switch (permissions[i]) {
                        case Manifest.permission.GET_ACCOUNTS:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "accounts granted");

                            }
                            break;
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "storage granted");

                            }
                            break;
                        case Manifest.permission.CALL_PHONE:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "call granted");

                            }
                            break;
                        case Manifest.permission.RECEIVE_SMS:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "sms granted");

                            }
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "location granted");

                            }
                            break;
                    }


                }

            }

        }
    }

    public void initViews(){
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

    }

    private void setMapMarker() {
        mMap.clear();

        //Location location
        final LatLng MELBOURNE = new LatLng(-37.813, 144.962);

        //set marker
        mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
        );

        //move camera to location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));

        //set zoom
        mMap.setMaxZoomPreference(21.0f);
        mMap.setMinZoomPreference(3.0f);

    }

    /*
    //This method animate camera.
    private void animateMapCamera(Result result){
        String addressType = result.getAddressComponentFirst().getTypeFirst();
        Log.d("myTag", addressType);
        switch (addressType) {
            case "street_number":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                break;
            case "route":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                break;
            case "locality":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                break;
            case "administrative_area_level_2":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(9.0f));
                break;
            case "administrative_area_level_1":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(7.0f));
                break;
            case "country":
                mMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f));
                break;
            default:
        }
    }*/

}
