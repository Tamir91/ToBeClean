package map.mvp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.PlaceAutocompleteAdapter;
import app.App;
import base.mvp.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.RecyclingSpot;
import tobeclean.tobeclean.R;


/**
 * Created by tamir on 05/02/18.
 */

public class MapFragment extends BaseFragment implements MapContract.View, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapFragment.class.getSimpleName();
    public static final Float DEFAULT_ZOOM = 15f;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int AUTO_COMP_REQ_CODE = 2;


    @BindView(R.id.etSearch)
    AutoCompleteTextView mSearchText;

    @BindView(R.id.fabFoundUserLocation)
    FloatingActionButton bFoundUserLocation;

    @BindView(R.id.map)
    MapView mapView;

    @Inject
    Context context;

    @Inject
    MapContract.Presenter presenter;

    @Inject
    PlaceAutocompleteAdapter autocompleteAdapter;

    @Inject
    Geocoder geocoder;

    @Inject
    GeoDataClient dataClient;

    //vars
    private GoogleMap map;
    private Marker marker;
    public LocationManager locationManager;
    private Location currentLocation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment_activity, container, false);
        ButterKnife.bind(this, view);
        //getLocationPermission();

        App.getApp(getContext())
                .getMapComponent()
                .inject(this);

        //attach view to presenter
        presenter.attachView(this);

        //set adapter
        mSearchText.setAdapter(autocompleteAdapter);

        // Register a listener that receives callbacks when a suggestion has been selected
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mapView != null) {

            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            mapView.getMapAsync(this);

            //initListener();
        } else {
            Log.e(TAG, "onMapReady: Error - Map Fragment was null");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady: map is ready");

        map = googleMap;

        Log.d(TAG, "OnMapReady: LocationPermissionsGranted = true");
        //getDeviceLocation();

        if (isHasPermissions()) {
            //view is ready to work
            presenter.viewIsReady();
        }
    }

    @Override
    @SuppressLint("MissingPermission")
    public void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation::in");
        if (isHasPermissions()) {
            currentLocation = locationManager.getLastKnownLocation("gps");
            Log.d(TAG, "getLastKnownLocation::have permissions");
        }
    }

    /**
     * This function find location and set marker on map
     */
    @Override
    public void findLocation() {
        Log.d(TAG, "findLocation::geo locating");

        if (currentLocation != null) {
            setMapMarker(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

        }

    }

    /**
     * This function request location updates
     *
     * @param provider    String
     * @param minTime     long
     * @param minDistance float
     */
    @SuppressLint("MissingPermission")
    @Override
    public void updateLocation(String provider, long minTime, float minDistance) {
        if (isHasPermissions() && locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
            return;
        }
        Log.e(TAG, "updateLocation::failed NOT HAVE PERMISSIONS");
    }

    /**
     * Move camera
     *
     * @param latLng {@link LatLng}
     * @param zoom   {@link Float}
     */
    public void moveCamera(@NonNull LatLng latLng, Float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    /**
     * Move camera to current Location
     *
     * @param zoom {@link Float}
     */
    @Override
    public void moveCameraToUserLocation(Float zoom) {
        if (map != null && currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

            Log.d(TAG, "moveCameraToUserLocation::moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        }
    }

    @Override
    public void showData(ArrayList<RecyclingSpot> list) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void setMyLocationVisibility(Boolean condition) {
        if (isHasPermissions()) {
            map.setMyLocationEnabled(condition);
        }
    }

    @Override
    public void setMyLocationButtonVisibility(Boolean condition) {

        map.getUiSettings().setMyLocationButtonEnabled(condition);
    }

    /**
     * Set marker on map
     *
     * @param latLng {@link LatLng}
     */
    public void setMapMarker(LatLng latLng) {
        Log.d(TAG, "setMapMarker::in");
        if (map != null){
            map.clear();

            //set marker
            marker = map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_marker))
                    .draggable(true)
            );
        }
    }

    /**
     * This function set max and min zoom in map.
     *
     * @param maxZoom {@link Float}
     * @param minZoom {@link Float}
     */
    public void setZoomPreference(Float maxZoom, Float minZoom) {
        if (map != null) {
            map.setMaxZoomPreference(maxZoom);
            map.setMinZoomPreference(minZoom);

            Log.d(TAG, "setZoomPreference::zoom was set " + "max = " + maxZoom + " min = " + minZoom);
        } else {
            Log.e(TAG, "setZoomPreference::failed. map null");
        }
    }

    /**
     * Show soft keyboard
     *
     * @return boolean
     */
    @Override
    public boolean showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.showSoftInput(mSearchText, InputMethodManager.SHOW_IMPLICIT);

    }

    /**
     * Hide soft keyword
     *
     * @return boolean
     */
    @Override
    public boolean hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
    }

    /**
     * Check Fine and Coarse permissions
     *
     * @return boolean
     */
    public boolean isHasPermissions() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @OnClick(R.id.fabFoundUserLocation)
    public void onClickFoundUserLocation() {
        Log.d(TAG, "onClickFoundUserLocation::FAB was clicked");

        //test
        //addBottleToFrame();
        //test
        addCustomMarker();

        presenter.onFoundUserLocationPressed();
    }

    public FrameLayout createFrame() {
        return getActivity().findViewById(R.id.place_frame);
    }

    public void addBottleToFrame() {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout frameLayout = (LinearLayout) inflater.inflate( R.layout.place_frame, null );
//
//
//
//
//        frameLayout.setDrawingCacheEnabled(true);
//        frameLayout.buildDrawingCache();
//
//        Bitmap bm = frameLayout.getDrawingCache();
//
//
//        //Todo bm = null!
//        Marker myMarker = map.addMarker(new MarkerOptions()
//                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
//                .icon(BitmapDescriptorFactory.fromBitmap(bm)));

    }

    private void addCustomMarker() {
        Log.d(TAG, "addCustomMarker()");
        if (map == null) {
            return;
        }

        // adding a marker on map with image from  drawable
        map.addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                .draggable(true)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(getMarkerBitmapFromView())));
    }

    private Bitmap getMarkerBitmapFromView() {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.place_frame, null);
        customMarkerView.findViewById(R.id.imPlastic).setVisibility(View.VISIBLE);

//        for (int count = 0; count < arrayID.length; ++count) {
//            ImageView markerImageView = customMarkerView.findViewById(arrayID[count]);
//            markerImageView.setImageResource(arraySources[count]);
//        }


//        ImageView markerImageView = customMarkerView.findViewById(R.id.imGlass);
//        ImageView markerImageView2 = customMarkerView.findViewById(R.id.imPlastic);
//        ImageView markerImageView3 = customMarkerView.findViewById(R.id.imPaper);
//        ImageView markerImageView4 = customMarkerView.findViewById(R.id.imBox);
//
//        markerImageView.setImageResource(resId);
//        markerImageView2.setImageResource(resId2);
//        markerImageView3.setImageResource(resId3);
//        markerImageView4.setImageResource(resId4);


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());

        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            setMapMarker(new LatLng(location.getLatitude(), location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onLocationChanged::onStatusChanged::" + provider + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onLocationChanged::onProviderEnabled::" + provider);

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onLocationChanged::onProviderDisabled::" + provider);
        }
    };

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data Client
     * to retrieve more details about the place.
     *
     * @see GeoDataClient#getPlaceById(String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Retrieve the place ID of the selected item from the Adapter.
            // The adapter stores each Place suggestion in a AutocompletePrediction from which we
            // read the place ID and title.
            final AutocompletePrediction item = autocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            // Issue a request to the Places Geo Data Client to retrieve a Place object with
            // additional details about the place.
            Task<PlaceBufferResponse> placeResult = dataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);

            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

            hideSoftKeyboard();
        }
    };


    /**
     * Callback for results from a Places Geo Data Client query that shows the first place result in
     * the details view on screen.
     */
    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {

        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {

            try {
                PlaceBufferResponse places = task.getResult();


                // Get the Place object from the buffer.
                final Place place = places.get(0);

                //user searching another spot on map
                locationManager.removeUpdates(locationListener);

                setMapMarker(place.getLatLng());
                moveCamera(place.getLatLng(), DEFAULT_ZOOM);
               /* // Format details of the place for display and show it in a TextView.
                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));

                // Display the third party attributions if set.
                final CharSequence thirdPartyAttribution = places.getAttributions();
                if (thirdPartyAttribution == null) {
                    mPlaceDetailsAttribution.setVisibility(View.GONE);
                } else {
                    mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                    mPlaceDetailsAttribution.setText(
                            Html.fromHtml(thirdPartyAttribution.toString()));
                }*/

                Log.i(TAG, "Place details received: " + place.getName());

                places.release();
            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete.", e);
                return;
            }
        }
    };


    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed::" + connectionResult.getErrorMessage());
    }
}

