package app;

import android.app.Application;
import android.content.Context;

import app.dagger.AppComponent;
import app.dagger.AppModule;
import app.dagger.DaggerAppComponent;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import utils.LocaleHelper;

/**
 * This class help create a components for ToBeClean App
 */

public class App extends Application {

    private static AppComponent appComponent;
    private static PlacesComponent placesComponent;
    private static MapComponent mapComponent;


    /**
     * @return App
     */
    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
        placesComponent = appComponent.placesBuilder().build();
        mapComponent = appComponent.mapBuilder().build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    /**
     * @return PlacesComponent
     */
    public PlacesComponent getPlacesComponent() {
        return placesComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public MapComponent getMapComponent() {
        return mapComponent;
    }
}