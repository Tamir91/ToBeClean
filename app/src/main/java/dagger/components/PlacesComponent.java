package dagger.components;


import javax.inject.Singleton;

import adapters.RecyclerAdapter;
import dagger.Subcomponent;
import dagger.modules.PlacesModule;
import dagger.scopes.PlacesScope;
import places.mvp.PlacesFragment;
import places.mvp.PlacesPresenter;

@PlacesScope
@Subcomponent(modules = {PlacesModule.class})
public interface PlacesComponent {

    void inject(PlacesFragment placesFragment);

    @Subcomponent.Builder
    interface Builder {

        PlacesComponent.Builder placesModule(PlacesModule placesModule);

        PlacesComponent build();
    }


}
