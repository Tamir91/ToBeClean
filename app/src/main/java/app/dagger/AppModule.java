package app.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.StreetViewPanoramaFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import base.mvp.BaseFragment;
import dagger.Module;
import dagger.Provides;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import helpers.TinyDB;
import map.mvp.MapFragment;
import places.mvp.PlacesFragment;
import storage.Preferences;

@Module(subcomponents = {MapComponent.class, PlacesComponent.class})
public class AppModule {
    private final Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    Preferences providePreferences() {
        return new Preferences(context);
    }

    @Singleton
    @Provides
    public TinyDB provideTinyDB(Context context) {
        return new TinyDB(context);
    }

    @Singleton
    @Provides
    public MapFragment provideMapFragment() {
        return new MapFragment();
    }

    @Singleton
    @Provides
    public PlacesFragment providePlacesFragment() {
        return new PlacesFragment();
    }

    @Singleton
    @Provides
    public List<BaseFragment> provideFragments(MapFragment mapFragment, PlacesFragment placesFragment) {
        List<BaseFragment> list = new ArrayList<>();
        list.add(mapFragment);
        list.add(placesFragment);
        return list;
    }
}
