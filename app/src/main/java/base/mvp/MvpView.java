package base.mvp;

import java.util.List;

import model.RecyclingStation;

public interface MvpView {

    void showError(String s);

    void showData(List<RecyclingStation> list);
}
