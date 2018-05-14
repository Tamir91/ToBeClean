package places.mvp;

import base.mvp.BasePresenter;

public class PlacesPresenter extends BasePresenter<PlacesContract.View> implements PlacesContract.Presenter {

    private PlacesContract.View view;

    public void onCreate(PlacesContract.View view) {
        this.view = view;
    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public PlacesContract.View getView() {
        return view;
    }
}
