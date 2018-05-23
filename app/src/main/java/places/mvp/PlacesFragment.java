package places.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.RecyclerAdapter;
import app.App;
import base.mvp.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.RecyclingSpot;
import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/02/18.
 */

public class PlacesFragment extends BaseFragment implements PlacesContract.View {

    private final String TAG = PlacesFragment.class.getSimpleName();

    ArrayList<RecyclingSpot> recyclingSpots;

    @BindView(R.id.recyclerViewPlaces)
    protected RecyclerView recyclerView;

    @Inject
    RecyclerAdapter adapter;

    @Inject
    PlacesContract.Presenter presenter;
    //TODO change a type in PlacesModule

    //Todo Change position with real position
    int currentPosition;


    public PlacesFragment() {
        Log.d(TAG, "PlacesFragment was created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycle_view_places, container, false);
        ButterKnife.bind(this, view);

        App.getApp(getContext())
                .getPlacesComponent()
                .inject(this);


        //attach view to presenter
        presenter.attachView(this);

        //view is ready to work

        presenter.viewIsReady();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.detachView();

        presenter.destroy();
    }

    /**
     * @param list ArrayList<RecyclingSpot>
     */
    @Override
    public void showData(ArrayList<RecyclingSpot> list) {
        Log.d(TAG, "showData::in");

        if (list.size() < 1) {
            adapter.addItems(getMockList());
        } else {
            adapter.addItems(list);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);

        adapter.notifyItemChanged(currentPosition);
    }

    /**
     * Close fragment
     */
    @Override
    public void close() {
        //??onDestroy();
    }

    public void setImg(int currentPosition, int imgID) {
        this.currentPosition = currentPosition;
        RecyclingSpot recyclingSpot = recyclingSpots.get(currentPosition);
        recyclingSpot.setImgID(imgID);
    }

    private ArrayList<RecyclingSpot> getMockList() {

        ArrayList<RecyclingSpot> tasks = new ArrayList<>();
        tasks.add(new RecyclingSpot("create app", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("buy google", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish homework", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project1", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project2", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project3", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project4", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project5", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project6", R.mipmap.ic_launcher_round));
        tasks.add(new RecyclingSpot("finish project7", R.mipmap.ic_launcher_round));

        return tasks;
    }


}
