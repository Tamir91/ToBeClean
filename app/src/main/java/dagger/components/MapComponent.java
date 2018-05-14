package dagger.components;


import dagger.Subcomponent;
import dagger.modules.MapModule;
import dagger.scopes.MapScope;
import map.mvp.MapPresenter;

@MapScope
@Subcomponent(modules = MapModule.class)
public interface MapComponent {

    void inject(MapPresenter mapPresenter);

    @Subcomponent.Builder
    interface Builder {

        MapComponent.Builder mapModule(MapModule mapModule);

        MapComponent build();
    }
}