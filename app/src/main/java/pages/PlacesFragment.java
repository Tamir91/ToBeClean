package pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import adapters.RecyclerAdapter;
import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/02/18.
 */

public class PlacesFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRecycleView();
    }

    private void setRecycleView() {
        //What need here getActivity? getContext?
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerViewPlaces);
        RecyclerAdapter adapter = new RecyclerAdapter(getMockList(), getContext());

        recyclerView.setHasFixedSize(true);
        //Todo check parameters in Constructor StaggeredGridLayoutManager
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//Todo upgrade to new ItemAnimator

        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> getMockList(){


        /*ListItemModel fox = new ListItemModel(R.mipmap.fox,"Fox","What does the fox say");
        ListItemModel pig = new ListItemModel(R.mipmap.pig,"Pig","Pig saying oink oink");
        ListItemModel frog = new ListItemModel(R.mipmap.frog,"Frog","Frog say ribbit");
        ListItemModel wolf = new ListItemModel(R.mipmap.wolf,"Wolf","Also known as the timber wolf");*/
        ArrayList<String> animalsList = new ArrayList<>();
        animalsList.add("What does the fox say");
        animalsList.add("Pig saying oink oink");
        animalsList.add("Frog say rabbit");
        animalsList.add("Also known as the timber wolf");

        return  animalsList;


    }
}
