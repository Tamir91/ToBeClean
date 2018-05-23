package model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

public class PlaceItemFrame extends LinearLayout {

    private RecyclingSpot recyclingSpot;

    public PlaceItemFrame(@NonNull Context context) {
        super(context);
        recyclingSpot = new RecyclingSpot();
    }


    public RecyclingSpot getRecyclingSpot() {
        return recyclingSpot;
    }

    public void setRecyclingSpot(RecyclingSpot recyclingSpot) {
        this.recyclingSpot = recyclingSpot;
    }
}
