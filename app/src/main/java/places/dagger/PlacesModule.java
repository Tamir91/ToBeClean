package places.dagger;

import base.dagger.ActivityModule;
import dagger.Module;
import dagger.Provides;
import model.PlaceItem;

@Module
public class PlacesModule implements ActivityModule {

        @Provides
        PlaceItem providePlaceItem() {
            return new PlaceItem();
        }

}
