package dagger.modules;

import android.content.Context;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import model.RecyclingSpot;
import places.mvp.PlacesContract;
import places.mvp.PlacesPresenter;

@Module
public class PlacesModule implements ActivityModule{

    @PlacesScope
    @Provides
    RecyclingSpot providePlaceItem() {
        return new RecyclingSpot();
    }

    @PlacesScope
    @Provides
    public ArrayList<RecyclingSpot> provideList(RecyclingSpot recyclingSpot) {
        return new ArrayList<>();
    }

    @PlacesScope
    @Provides
    public PlacesContract.Presenter providePlacesPresenter() {
        return new PlacesPresenter();
    }

    @PlacesScope
    @Provides
    RecyclerAdapter provideRecyclerAdapter(ArrayList<RecyclingSpot> items, Context context) {
        return new RecyclerAdapter(items, context);
    }
}