package dagger.modules;

import dagger.Module;
import dagger.Provides;
import dagger.scopes.MapScope;
import map.mvp.MapFragment;
import map.mvp.MapPresenter;
import places.mvp.PlacesFragment;

@Module
public class MapModule {

    @MapScope
    @Provides
    public MapPresenter provideMapPresenter() {
        return new MapPresenter();
    }

    @MapScope
    @Provides
    public PlacesFragment providePlacesFragment() {
        return new PlacesFragment();
    }
}
