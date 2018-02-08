package pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import helpers.PermissionHelper;
import tobeclean.tobeclean.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by tamir on 05/02/18.
 */

public class MapCleanFragment extends Fragment implements OnMapReadyCallback {

    private String  locationProvider;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Context context;
    private OnMap mOnMap;
    private PermissionHelper permissionHelper;

    public MapCleanFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMap){
            this.context = context;
            mOnMap = (OnMap) context;
        }
    }//onAttach



    @Override
    public void onDetach() {
        super.onDetach();
        mOnMap = null;
    }//onDetach

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        permissionHelper = new PermissionHelper(getActivity());
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(criteria,true);


        locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                double lat= location.getLatitude();
                double lng = location.getLongitude();
                Toast.makeText(getActivity(),"lat:"+lat+" long:"+lng, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(permissionHelper.getLocationPermission()) {
            locationUpdateRequest();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Activity activity = getActivity();

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        SupportMapFragment supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        //why supportMapFragment is null?
       /* SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()

                .findFragmentById(R.id.map);*/
        supportMapFragment.getMapAsync(this);
    }



    @SuppressLint("MissingPermission")
    public void locationUpdateRequest(){
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO change standard code
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private interface OnMap{
        //TODO change Name of interface
        //ToDo build interface if it need
    }
}
