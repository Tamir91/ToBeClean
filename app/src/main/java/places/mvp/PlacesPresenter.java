package places.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import base.mvp.BasePresenter;
import model.RecyclingContainer;
import model.RecyclingStation;

public class PlacesPresenter extends BasePresenter<PlacesContract.View> implements PlacesContract.Presenter {

    private final String TAG = PlacesPresenter.class.getSimpleName();

    @Override
    public void attachView(PlacesContract.View mvpView) {
        super.attachView(mvpView);
        Log.d(TAG, "attachView::" + mvpView.getClass().getSimpleName());
    }

    @Override
    public void viewIsReady() {
        Log.d(TAG, "viewIsReady::in");
        //Where from presenter get arrayList? Model?
        getView().showData(new ArrayList<RecyclingStation>());
    }

    @Override
    public void onStop() {

    }
}
