package map.mvp;

import base.mvp.MvpPresenter;
import base.mvp.MvpView;

public interface MapContract {

    interface View extends MvpView {
        void setMyLocationVisibility(Boolean condition);

        void setMyLocationButton(Boolean condition);

        void findLocation(String location);

        void getSearchingAddress(String address);

    }

    interface Presenter extends MvpPresenter<View> {
        void getSearchingAddress(String address);
    }

}
