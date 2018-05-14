package dagger.modules;

import android.content.Context;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import model.PlaceItem;
import places.mvp.PlacesPresenter;

@Module
public class PlacesModule {

    @PlacesScope
    @Provides
    PlaceItem providePlaceItem() {
        return new PlaceItem();
    }

    @PlacesScope
    @Provides
    public ArrayList<PlaceItem> provideList(PlaceItem placeItem) {
        return new ArrayList<>();
    }

    @PlacesScope
    @Provides
    public PlacesPresenter providePlacesPresenter() {
        return new PlacesPresenter();
    }

    @PlacesScope
    @Provides
    RecyclerAdapter provideRecyclerAdapter(ArrayList<PlaceItem> items, Context context) {
        return new RecyclerAdapter(items, context);
    }
}