package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.mthli.slice.Slice;
import model.RecyclingStation;
import tobeclean.tobeclean.R;

/**
 * This adapter class for Recycler view places
 * Created by tamir on 19/02/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private static final int VIEW_TYPE_TOP = 0x01;
    private static final int VIEW_TYPE_CENTER = 0x02;
    private static final int VIEW_TYPE_BOTTOM = 0x03;

    public ArrayList<RecyclingStation> stations;
    private Context context;

    /**
     * Constructor
     */
    public RecyclerAdapter(ArrayList<RecyclingStation> listItems, Context context) {
        this.context = context;
        this.stations = listItems;
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_BOTTOM;
        } else {
            return VIEW_TYPE_CENTER;
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TOP) {
            return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_top, parent, false));
        } else if (viewType == VIEW_TYPE_CENTER) {
            return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_center, parent, false));
        } else {
            return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_bottom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, int position) {
        RecyclingStation place = stations.get(position);
        holder.setViews(place);

        //Slice this 3-rd part library from GitHub. It do nice UI only.
        int viewType = getItemViewType(position);
        Slice slice = new Slice(holder.getFrame());
        slice.setElevation(2.0f);

        //If view on the top
        if (viewType == VIEW_TYPE_TOP) {
            slice.setRadius(8.0f);
            slice.showLeftTopRect(false);
            slice.showRightTopRect(false);
            slice.showRightBottomRect(true);
            slice.showLeftBottomRect(true);
            slice.showTopEdgeShadow(true);
            slice.showBottomEdgeShadow(false);
            //If view in the bottom
        } else if (viewType == VIEW_TYPE_BOTTOM) {
            slice.setRadius(8.0f);
            slice.showLeftTopRect(true);
            slice.showRightTopRect(true);
            slice.showRightBottomRect(false);
            slice.showLeftBottomRect(false);
            slice.showTopEdgeShadow(false);
            slice.showBottomEdgeShadow(true);
        } else {
            //If view in middle
            slice.setRadius(0.0f);
            slice.showTopEdgeShadow(false);
            slice.showBottomEdgeShadow(false);
        }
    }

    /**
     * This method compare old list items with new list.
     *
     * @param items {@link ArrayList<RecyclingStation>}
     * @return boolean
     */
    public boolean addItems(ArrayList<RecyclingStation> items) {
        return stations.addAll(items);
    }

    /**
     * This method clean list from all items*/
    public void cleanListItems() {
        stations = new ArrayList<>();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private FrameLayout frame;
        private TextView addressTextView;
        private ImageView imageView;


        //Constructor
        RecyclerHolder(View view) {
            super(view);

            this.frame = view.findViewById(R.id.frame);
            this.addressTextView = view.findViewById(R.id.titlePlace);
            this.imageView = view.findViewById(R.id.imgPlace);

            addressTextView.setTextColor(Color.BLACK);
            //imageView.setOnClickListener();
        }

        /**
         * @return FrameLayout
         */
        FrameLayout getFrame() {
            return frame;
        }

        void setViews(RecyclingStation station) {
            //this.addressTextView.setText(R.string.app_name);

            this.addressTextView.setText(station.getAddress());
            this.imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
