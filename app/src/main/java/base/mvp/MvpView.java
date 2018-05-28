package base.mvp;

import java.util.ArrayList;
import java.util.List;

import model.RecyclingContainer;
import model.RecyclingStation;

public interface MvpView {

    void showError(String s);

    void showData(ArrayList<RecyclingStation> list);
}
