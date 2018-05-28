package dagger.modules;

import android.content.Context;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import model.RecyclingContainer;
import model.RecyclingStation;
import places.mvp.PlacesContract;
import places.mvp.PlacesPresenter;

@Module
public class PlacesModule implements ActivityModule{

    @PlacesScope
    @Provides
    RecyclingStation provideRecyclingStation() {
        return new RecyclingStation();
    }
    
    @PlacesScope
    @Provides
    public ArrayList<RecyclingStation> provideList(RecyclingStation station) {
        return new ArrayList<>();
    }

    @PlacesScope
    @Provides
    public PlacesContract.Presenter providePlacesPresenter() {
        return new PlacesPresenter();
    }

    @PlacesScope
    @Provides
    RecyclerAdapter provideRecyclerAdapter(ArrayList<RecyclingStation> items, Context context) {
        return new RecyclerAdapter(items, context);
    }
}