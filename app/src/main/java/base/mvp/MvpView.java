package base.mvp;

import java.util.ArrayList;

import model.PlaceItem;

public interface MvpView {

    void showError(String s);

    void showData(ArrayList<PlaceItem> list);
}
