package app.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import helpers.RuntimePermissionHelper;
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
}
