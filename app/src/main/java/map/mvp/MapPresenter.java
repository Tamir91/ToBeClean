package map.mvp;

import android.util.Log;

import base.mvp.BasePresenter;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {

    private final String TAG = MapPresenter.class.getSimpleName();


    @Override
    public void attachView(MapContract.View mvpView) {
        super.attachView(mvpView);
        Log.d(TAG, "attachView::" + mvpView.getClass().getSimpleName());
    }


    @Override
    public void viewIsReady() {
        Log.d(TAG, "viewIsReady::in");
    }

    @Override
    public void onStop() {

    }


    @Override
    public void getSearchingAddress(String address) {

    }
}
