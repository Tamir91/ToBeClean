package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.CleanConstants;
import helpers.TinyDB;
import io.github.mthli.slice.Slice;
import model.RecyclingStation;
import tobeclean.tobeclean.R;

/**
 * This adapter class for Recycler view places
 * Created by tamir on 19/02/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_TOP = 0x01;
    private static final int VIEW_TYPE_CENTER = 0x02;
    private static final int VIEW_TYPE_BOTTOM = 0x03;
    private static final String GOOGLE_MAP_ADDRESS = "https://maps.google.com/?q=";



    public ArrayList<RecyclingStation> stations;
    private Context context;
    private TinyDB tinyDB;

    /**
     * Constructor
     */
    public RecyclerAdapter(ArrayList<RecyclingStation> listItems, Context context) {
        this.context = context;
        this.stations = listItems;
        tinyDB = new TinyDB(context);
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
        RecyclingStation station = stations.get(position);
        holder.setViews(station);

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

    /***/
    public void deleteItem(View view){
    }

    //share location
    public void shareLocation(String address) {
        Log.d(TAG, "shareStationLocation");
        String link = GOOGLE_MAP_ADDRESS + address;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, link);

        Intent new_intent = Intent.createChooser(shareIntent, "Share via");
        new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(new_intent);
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private FrameLayout frame;
        private TextView addressTextView;
        private ImageView imageView;
        private ImageButton shareButton;


        //Constructor
        RecyclerHolder(final View view) {
            super(view);

            init(view);
            initListeners(view);
        }

        //init views
        private void init(@NonNull View view) {
            this.frame = view.findViewById(R.id.frame);
            this.addressTextView = view.findViewById(R.id.titlePlace);
            this.imageView = view.findViewById(R.id.imgPlace);
            this.shareButton = view.findViewById(R.id.imageButton);

            addressTextView.setTextColor(Color.BLACK);
        }

        //init listeners
        private void initListeners(@NonNull final View view) {
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shareStationLocation(addressTextView.getText().toString());
                    //tinyDB.clear();
                    tinyDB.remove(CleanConstants.ADDRESS);

                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //tinyDB.clear();
                    tinyDB.remove(CleanConstants.ADDRESS);
                    Toast.makeText(context, "ffff", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        /**
         * @return FrameLayout
         */
        FrameLayout getFrame() {
            return frame;
        }

        void setViews(RecyclingStation station) {
            this.addressTextView.setText(station.getAddress());
            this.imageView.setImageResource(R.mipmap.ic_launcher);
            this.shareButton.setImageResource(R.mipmap.ic_share);
        }
    }
}
