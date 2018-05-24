package dagger.components;

import dagger.Subcomponent;
import dagger.modules.PlacesModule;
import dagger.scopes.PlacesScope;
import places.mvp.PlacesFragment;

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
