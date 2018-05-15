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
import model.PlaceItem;
import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/02/18.
 */

public class PlacesFragment extends BaseFragment implements PlacesContract.View {

    private final String LOG = PlacesFragment.class.getSimpleName();

    ArrayList<PlaceItem> placeItems;

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
        Log.d(LOG, "PlacesFragment was created");
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
     * @param list ArrayList<PlaceItem>
     */
    @Override
    public void showData(ArrayList<PlaceItem> list) {
        //recyclerView = null;
        Log.d(LOG, "showData::in");

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
        PlaceItem placeItem = placeItems.get(currentPosition);
        placeItem.setImgID(imgID);
    }

    private ArrayList<PlaceItem> getMockList() {

        ArrayList<PlaceItem> tasks = new ArrayList<>();
        tasks.add(new PlaceItem("create app", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("buy google", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish homework", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project1", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project2", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project3", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project4", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project5", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project6", R.mipmap.ic_launcher_round));
        tasks.add(new PlaceItem("finish project7", R.mipmap.ic_launcher_round));

        return tasks;
    }


}
