package places.mvp;

import base.mvp.MvpPresenter;
import base.mvp.MvpView;

public interface PlacesContract {

    interface View extends MvpView {
        void close();
    }

    interface Presenter extends MvpPresenter<View> {
    }

}
