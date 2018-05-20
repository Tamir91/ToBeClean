package map.mvp;

import android.util.Log;

import base.mvp.BasePresenter;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {

    private final String TAG = MapPresenter.class.getSimpleName();
    private final float DEFAULT_ZOOM = 15;

    private final String GPS_PROVIDER = "gps";
    private final long minUpdatingTime = 10000;
    private final float minDistance = 5;

    @Override
    public void attachView(MapContract.View mvpView) {
        super.attachView(mvpView);
        Log.d(TAG, "attachView::" + mvpView.getClass().getSimpleName());
    }


    @Override
    public void viewIsReady() {
        Log.d(TAG, "viewIsReady::in");

        getView().setMyLocationVisibility(false);
        getView().setZoomPreference(21.0f, 3.0f);
        getView().getLastKnownLocation();
        getView().findLocation();
        getView().moveCameraToUserLocation(DEFAULT_ZOOM);

        getView().updateLocation(GPS_PROVIDER, minUpdatingTime, minDistance);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFoundUserLocationPressed() {
        Log.d(TAG, "onFoundUserLocationPressed::in");

        getView().hideSoftKeyboard();
        getView().updateLocation(GPS_PROVIDER, minUpdatingTime, minDistance);
        getView().findLocation();
        getView().moveCameraToUserLocation(DEFAULT_ZOOM);
    }
}
