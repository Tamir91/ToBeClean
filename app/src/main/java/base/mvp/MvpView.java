package base.mvp;

import java.util.List;

import model.RecyclingContainer;
import model.RecyclingStation;

public interface MvpView {

    void showError(String s);

    void showData(List<RecyclingStation> list);
}
