package places.mvp;

import android.util.Log;

import java.util.ArrayList;

import base.mvp.BasePresenter;
import model.PlaceItem;

public class PlacesPresenter extends BasePresenter<PlacesContract.View> implements PlacesContract.Presenter {

    private final String TAG = PlacesPresenter.class.getSimpleName();

   /* public void onCreate(PlacesContract.View view) {
        this.view = view;
    }*/

    @Override
    public void attachView(PlacesContract.View mvpView) {
        super.attachView(mvpView);
        Log.d(TAG, "attachView::" + mvpView.getClass().getSimpleName());
    }

    @Override
    public void viewIsReady() {
        Log.d(TAG, "viewIsReady::in");
        //Where from presenter get arrayList? Model?
        getView().showData(new ArrayList<PlaceItem>());
    }

    @Override
    public void onStop() {

    }
}
