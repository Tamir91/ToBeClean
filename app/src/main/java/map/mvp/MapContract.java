package map.mvp;

import base.mvp.MvpPresenter;
import base.mvp.MvpView;

public interface MapContract {

    interface View extends MvpView {
        void setMyLocationVisibility(Boolean condition);

        void setMyLocationButtonVisibility(Boolean condition);

        void findLocation(String location);

        void getSearchingAddress(String address);

        void setZoomPreference(Float maxZoom, Float minZoom);


    }

    interface Presenter extends MvpPresenter<View> {
        void getSearchingAddress(String address);
    }

}
