package dagger.modules;

import android.content.Context;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import model.PlaceItem;
import places.mvp.PlacesContract;
import places.mvp.PlacesPresenter;

@Module
public class PlacesModule implements ActivityModule{

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

    @PlacesScope
    @Provides
    PlacesContract.Presenter proviedPlacesPresenter() {
        return new  PlacesPresenter();
    }
}