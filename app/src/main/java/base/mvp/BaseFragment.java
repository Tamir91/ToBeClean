package base.mvp;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import javax.inject.Inject;

public abstract class BaseFragment extends Fragment implements MvpView {


       // protected abstract MvpPresenter getPresenter;

    @Override
    public void showError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (getPresenter() != null) {
            getPresenter().onStop();
        }*/
    }
}
