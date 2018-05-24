package app.dagger;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import helpers.RuntimePermissionHelper;
import model.MockDataProvider;
import model.RecyclingStation;
import storage.Preferences;
import tobeclean.tobeclean.BaseActivity;
import utils.AppViewModel;

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

    /*@Singleton
    @Provides
    List<RecyclingStation> provideRecyclingStations() {
        return new MockDataProvider().getRecyclingStationList();
    }*/


}
