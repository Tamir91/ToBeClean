package dagger.modules;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import app.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import dagger.scopes.PlacesScope;
import helpers.OnItemTouchListener;
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
    public ArrayList<RecyclingStation> provideList() {
        return new ArrayList<>();
    }

    @PlacesScope
    @Provides
    public PlacesContract.Presenter providePlacesPresenter() {
        return new PlacesPresenter();
    }

    @PlacesScope
    @Provides
    public OnItemTouchListener provideOnItemTouchListener() {
        return new OnItemTouchListener() {
            @Override
            public void onCardViewTap(View view, int position) {

            }

            @Override
            public void onButtonShareClick(View view, int position) {

            }
        };
    }

    @PlacesScope
    @Provides
    RecyclerAdapter provideRecyclerAdapter(ArrayList<RecyclingStation> items, OnItemTouchListener listener, Context context) {
        return new RecyclerAdapter(items, listener, context);
    }
}