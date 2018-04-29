package places.mvp;

import base.mvp.MvpModel;
import dagger.Module;
import dagger.Provides;
import model.PlaceItem;

@Module
public class PlacesModel implements MvpModel {

    /**
     * @return PlaceItem
     */
    @Provides
    PlaceItem providePlaceItem() {
        return new PlaceItem();
    }

}
