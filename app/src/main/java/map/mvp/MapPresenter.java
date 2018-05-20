package map.mvp;

import android.util.Log;

import base.mvp.BasePresenter;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {

    private final String TAG = MapPresenter.class.getSimpleName();
    private final String GPS_PROVIDER = "gps";
    private long minUpdatingTime = 10000;
    private float minDistance = 5;


    @Override
    public void attachView(MapContract.View mvpView) {
        super.attachView(mvpView);
        Log.d(TAG, "attachView::" + mvpView.getClass().getSimpleName());
    }


    @Override
    public void viewIsReady() {
        Log.d(TAG, "viewIsReady::in");

        getView().setMyLocationVisibility(true);
        getView().setMyLocationButtonVisibility(true);
        getView().setZoomPreference(21.0f, 3.0f);
        getView().updateLocation(GPS_PROVIDER, minUpdatingTime, minDistance);
        getView().findLocation();
    }

    @Override
    public void onStop() {

    }


    @Override
    public void onFoundUserLocationPressed() {
        Log.d(TAG, "onFoundUserLocationPressed::in");

        getView().hideSoftKeyboard();
        getView().setMyLocationVisibility(true);
        getView().updateLocation(GPS_PROVIDER, minUpdatingTime, minDistance);
        getView().findLocation();
    }
}
