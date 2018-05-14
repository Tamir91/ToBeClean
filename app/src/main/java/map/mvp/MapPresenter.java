package map.mvp;

import android.content.Context;
import android.content.Intent;

import base.mvp.MvpView;

public class MapPresenter implements ContractMap.Presenter  {

    public static void start(Context context) {
        Intent starter = new Intent(context, MapPresenter.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public void attachView(MvpView mvpView) {

    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onStop() {

    }
}
