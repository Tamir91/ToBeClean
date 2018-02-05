package pages;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by tamir on 05/02/18.
 */

public class MapCleanFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private OnMap mOnMap;

    public MapCleanFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMap){
            mOnMap = (OnMap) context;
        }
    }//onAttach



    @Override
    public void onDetach() {
        super.onDetach();
        mOnMap = null;
    }//onDetach


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
