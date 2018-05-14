package app.dagger;

import javax.inject.Singleton;

import dagger.Component;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import tobeclean.tobeclean.MainActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectMain(MainActivity mainActivity);

    PlacesComponent.Builder placesBuilder();

    MapComponent.Builder mapBuuilder();
}