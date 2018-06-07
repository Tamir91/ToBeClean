package places.mvp;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;

import javax.inject.Inject;

import adapters.RecyclerAdapter;
import app.App;
import base.mvp.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import helpers.CleanConstants;
import helpers.OnItemTouchListener;
import helpers.TinyDB;
import model.RecyclingStation;
import tobeclean.tobeclean.R;

import static helpers.CleanConstants.GOOGLE_MAP_ADDRESS;

/**
 * Created by tamir on 05/02/18.
 */

public class PlacesFragment extends BaseFragment implements PlacesContract.View {

    private final String TAG = PlacesFragment.class.getSimpleName();


    //ButterKnife
    @BindView(R.id.recyclerViewPlaces)
    protected RecyclerView recyclerView;

    private Unbinder unbinder = null;

    //DI
    @Inject
    Context context;

    @Inject
    RecyclerAdapter adapter;

    @Inject
    TinyDB tinyDB;

    @Inject
    PlacesContract.Presenter presenter;

    @Inject
    public ArrayList<RecyclingStation> stations;


    public PlacesFragment() {
        Log.d(TAG, TAG + " was created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycle_view_places, container, false);
        unbinder = ButterKnife.bind(this, view);

        App.getApp(getContext())
                .getPlacesComponent()
                .inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        initListeners();


        //attach view to presenter
        presenter.attachView(this);

        //view is ready to work
        presenter.viewIsReady();

        return view;
    }

    /**Init listeners for this fragment*/
    private void initListeners() {
        //Swipe Delete in Recycler view
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(context, stations.get(position).getAddress() + " deleted", Toast.LENGTH_SHORT).show();

                                    removeStationFromTinyDB(stations.get(position).getAddress());
                                    stations.remove(position);
                                    adapter.removeItem(position);
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(context, stations.get(position).getAddress() + " deleted", Toast.LENGTH_SHORT).show();

                                    removeStationFromTinyDB(stations.get(position).getAddress());
                                    stations.remove(position);
                                    adapter.removeItem(position);
                                }
                            }
                        });

        recyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        adapter.cleanListItems();
        presenter.detachView();
        presenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//Unbind ButterKnife
    }

    /**
     * Remove favorite station From
     */
    public void removeStationFromTinyDB(String address) {
        ArrayList<Object> objects = tinyDB.getListObject(CleanConstants.ADDRESS, RecyclingStation.class);

        for (Object o : objects) {
            if (((RecyclingStation) o).getAddress().equals(address)) {
                objects.remove(o);
                tinyDB.putListObject(CleanConstants.ADDRESS, objects);
                Log.d(TAG, "removeStationFromTinyDB::station with address " + address + " was removed from tinyDB");
                return;
            }
        }
    }

    /**
     * This function not work for favorite places. TODO Need changes in param and data
     *
     * @param list ArrayList<RecyclingContainer>
     */
    @Override
    public void showData(ArrayList<RecyclingStation> list) {
        Log.d(TAG, "showData::in");
        stations.clear();
        adapter.cleanListItems();

        ArrayList<Object> objects = tinyDB.getListObject(CleanConstants.ADDRESS, RecyclingStation.class);
        Log.d(TAG, "showData::" + "objects = " + objects.size());

        for (Object o : objects) {
            stations.add((RecyclingStation) o);

        }
        Log.d(TAG, "showData::" + "stations = " + stations.size());
        adapter.addItems(stations);
    }

    /**
     * Close fragment
     */
    @Override
    public void close() {
        //??onDestroy();
    }


//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof RecyclerAdapter.RecyclerHolder) {
//            String name = stations.get(viewHolder.getAdapterPosition()).getAddress();
//
//            final RecyclingStation deletedStation = stations.get(viewHolder.getAdapterPosition());
//            final int deleteIndex = viewHolder.getAdapterPosition();
//
//            adapter.removeItem(deleteIndex);
//
//            Snackbar snackbar = Snackbar.make(getView(), name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    adapter.restoreItem(deletedStation, deleteIndex);
//                }
//            });
//
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
//        }
//    }
}
