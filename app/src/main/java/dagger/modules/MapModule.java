package dagger.modules;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import adapters.PlaceAutocompleteAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.MapScope;
import map.mvp.MapContract;
import map.mvp.MapPresenter;

@Module
public class MapModule implements ActivityModule {

    private final double lat2 = 33.24;
    private final double lng2 = 35.51;
    private final double lat1 = 29.30;
    private final double lng1 = 34.15;

    @MapScope
    @Provides
    public MapContract.Presenter provideMapPresenter() {
        return new MapPresenter();
    }

    @MapScope
    @Provides
    public Geocoder provideGeocoder(Context context) {
        return new Geocoder(context);
    }

    @MapScope
    @Provides
    public GeoDataClient provideGeoDataClient(Context context) {
       /* return new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                //.enableAutoManage(, context)
                .build();*/
        return Places.getGeoDataClient(context);
    }

    @MapScope
    @Provides
    public LatLng provideLatLng(double lat, double lng) {
        return new LatLng(lat, lng);
    }

    @MapScope
    @Provides
    public LatLngBounds providesBounds() {
        return new LatLngBounds(provideLatLng(lat1, lng1), provideLatLng(lat2, lng2));
        //return  new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    }

    @MapScope
    @Provides
    public PlaceAutocompleteAdapter providePlaceAutocompleteAdapter(Context context,
                                                                    GeoDataClient client,
                                                                    LatLngBounds bounds) {
        return new PlaceAutocompleteAdapter(context, client, bounds, null);
    }


    /*@MapScope
    @Provides
    public PlacesFragment providePlacesFragment() {
        return new PlacesFragment();
    }*/
}
