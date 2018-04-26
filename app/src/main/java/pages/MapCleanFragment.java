package pages;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tobeclean.tobeclean.R;


/**
 * Created by tamir on 05/02/18.
 */

public class MapCleanFragment extends Fragment {

    private static final String TAG = MapCleanFragment.class.getSimpleName();
    public static final Float DEFAULT_ZOOM = 15f;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private SupportMapFragment mapFragment;

    private EditText mSearchText;

    Context context;
    View view;

    //vars
    private GoogleMap map;
    private Marker marker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment_activity, container, false);
        context = getActivity();
        //getLocationPermission();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mapFragment.getMapAsync();
        initViews(view);


        if (mapFragment != null) {

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap gMap) {
                    Log.d(TAG, "OnMapReady: map is ready");

                    map = gMap;

                    Log.d(TAG, "OnMapReady: LocationPermissionsGranted = true");
                    //getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(false);

                    initListener();

                    Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();

                }

            });
        } else {

            Log.e(TAG, "onMapReady: Error - Map Fragment was null");

        }
    }

    /**
     * This function init views
     */
    public void initViews(View view) {
        Log.d(TAG, "initViews: in");
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mSearchText = view.findViewById(R.id.etSearch);
    }

    /**
     * This function init listeners for views
     */
    private void initListener() {
        Log.d(TAG, "init: initializing");

        /*This listener wait for action with Search field*/
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Log.d(TAG, "initListener: onEditorAction: in");

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {


                    findLocation();
                }
                return false;
            }
        });
    }

    /**
     * This function find location by address in search field
     */
    private void findLocation() {

        Log.d(TAG, "findLocation: geo locating");

        String searchStr = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());

        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchStr, 1);
        } catch (IOException e) {
            Log.e(TAG, "findLocation: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "findLocation: found a location: " + address.toString());
            Toast.makeText(getContext(), address.toString(), Toast.LENGTH_SHORT).show();

            //moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), );
            setMapMarker(new LatLng(address.getLatitude(), address.getLongitude()));
        }
    }

    /*set marker on map*/
    private void setMapMarker(LatLng latLng) {
        Log.d(TAG, "setMapMarker: in");
        map.clear();

        //set marker
        marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_marker))
                .draggable(true)
        );

        //move camera to location
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //set zoom
        setZoomPreference(21.0f, 3.0f);
    }

    /**
     * This function set max and min zoom in map.
     */
    public void setZoomPreference(Float maxZoom, Float minZoom) {
        if (map != null) {
            map.setMaxZoomPreference(maxZoom);
            map.setMinZoomPreference(minZoom);

            Log.d(TAG, "setZoomPreference: zoom was set");
        } else {
            Log.e(TAG, "setZoomPreference: failed. map null");
        }
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

    public void moveCamera(LatLng latLng, Float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

}
