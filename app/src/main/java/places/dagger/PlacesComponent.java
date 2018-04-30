package places.dagger;

import javax.inject.Singleton;

import dagger.Component;
import places.mvp.PlacesPresenter;

@Singleton
@Component(modules = {PlacesModule.class})
public interface PlacesComponent {

    void inject(PlacesPresenter placesPresenter);

}
