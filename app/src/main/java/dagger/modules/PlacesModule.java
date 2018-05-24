package dagger.modules;

import android.content.Context;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import model.RecyclingContainer;
import places.mvp.PlacesContract;
import places.mvp.PlacesPresenter;

@Module
public class PlacesModule implements ActivityModule{

    @PlacesScope
    @Provides
    RecyclingContainer provideRecyclingContainer() {
        return new RecyclingContainer("lol", 0);
    }
    
    @PlacesScope
    @Provides
    public ArrayList<RecyclingContainer> provideList(RecyclingContainer recyclingContainer) {
        return new ArrayList<>();
    }

    @PlacesScope
    @Provides
    public PlacesContract.Presenter providePlacesPresenter() {
        return new PlacesPresenter();
    }

    @PlacesScope
    @Provides
    RecyclerAdapter provideRecyclerAdapter(ArrayList<RecyclingContainer> items, Context context) {
        return new RecyclerAdapter(items, context);
    }
}