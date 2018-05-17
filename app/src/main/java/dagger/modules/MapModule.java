package dagger.modules;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import adapters.PlaceAutocompleteAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.MapScope;
import map.mvp.MapContract;
import map.mvp.MapFragment;
import map.mvp.MapPresenter;
import places.mvp.PlacesFragment;

@Module
public class MapModule implements ActivityModule{

    @MapScope
    @Provides
    public MapContract.Presenter provideMapPresenter() {
        return new MapPresenter();
    }

    @MapScope
    @Provides
    public GoogleApiClient provideApiClient(Context context){
        return new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                //.enableAutoManage(, context)
                .build();
    }

    @MapScope
    @Provides
    public LatLng provideLatLng(){
        return new LatLng(0, 0);
    }

    @MapScope
    @Provides
    public LatLngBounds providesBounds(LatLng latLng) {
        return new LatLngBounds(new LatLng(), new LatLng());
    }

    @MapScope
    @Provides
    public PlaceAutocompleteAdapter providePlaceAutocompleteAdapter(Context context,
                                                                    GoogleApiClient client,
                                                                    LatLngBounds bounds){
        return new  PlaceAutocompleteAdapter(context, client, bounds, null);
    }


    /*@MapScope
    @Provides
    public PlacesFragment providePlacesFragment() {
        return new PlacesFragment();
    }*/
}
