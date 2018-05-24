package app.dagger;

import javax.inject.Singleton;

import dagger.Component;
import dagger.components.MapComponent;
import dagger.components.PlacesComponent;
import model.MockDataProvider;
import tobeclean.tobeclean.BaseActivity;
import tobeclean.tobeclean.MainActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectMain(BaseActivity baseActivity);

    PlacesComponent.Builder placesBuilder();

    MapComponent.Builder mapBuilder();
}