package helpers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class help to work with google maps.
 * Created by ty on 02/04/18.
 */

public class CustomGoogleMap {

    /*GoogleMap mMap;

    public CustomGoogleMap(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    *//**This function *//*
    public void setMarker(){
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
    }*/
}