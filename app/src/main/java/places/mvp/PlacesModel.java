package places.mvp;

import base.mvp.MvpModel;
import dagger.Module;
import dagger.Provides;
import model.PlaceItem;


public class PlacesModel implements MvpModel {

    /**
     * @return PlaceItem
     */

    PlaceItem providePlaceItem() {
        return new PlaceItem();
    }

}
