package map.mvp;

import base.mvp.MvpPresenter;
import base.mvp.MvpView;

public interface MapContract {

    interface View extends MvpView {
        void setMyLocationVisibility(Boolean condition);

        void setMyLocationButtonVisibility(Boolean condition);

        void findLocation();

        void moveCameraToUserLocation(Float zoom);

        void getLastKnownLocation();

        void setZoomPreference(Float maxZoom, Float minZoom);

        void updateLocation(String provider, long minTime, float minDistance);

        boolean hideSoftKeyboard();

        boolean showSoftKeyboard();


    }

    interface Presenter extends MvpPresenter<View> {

        void onFoundUserLocationPressed();

    }

}
