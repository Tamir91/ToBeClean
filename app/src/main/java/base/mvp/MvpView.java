package base.mvp;

import java.util.ArrayList;

import model.RecyclingSpot;

public interface MvpView {

    void showError(String s);

    void showData(ArrayList<RecyclingSpot> list);
}
