package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;

import helpers.CleanConstants;
import helpers.OnItemTouchListener;
import helpers.TinyDB;
import io.bal.ihsan.streetapi.api.base.CallBack;
import io.bal.ihsan.streetapi.api.base.StreetView;
import io.github.mthli.slice.Slice;
import model.RecyclingStation;
import retrofit.Response;
import retrofit.Retrofit;
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


    private OnItemTouchListener onItemTouchListener;
    public ArrayList<RecyclingStation> stations;
    private Context context;
    private TinyDB tinyDB;

    /**
     * Constructor
     */
    public RecyclerAdapter(ArrayList<RecyclingStation> listItems, OnItemTouchListener onItemTouchListener, Context context) {
        this.context = context;
        this.stations = listItems;
        this.onItemTouchListener = onItemTouchListener;
        tinyDB = new TinyDB(context);
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

        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.addressTextView.setText(station.getAddress());


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

    @Override
    public int getItemCount() {
        return stations == null ? 0 : stations.size();
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

    /**
     * This method compare old list items with new list.
     *
     * @param items {@link ArrayList<RecyclingStation>}
     * @return boolean
     */
    public boolean addItems(ArrayList<RecyclingStation> items) {
        if (stations.addAll(items)){
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * This method clean list from all items
     */
    public void cleanListItems() {
        stations = new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Remove station from list
     *
     * @param item     {@link RecyclingStation}
     * @param position int position
     */
    public void removeItem(int position) {
        stations.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    /**
     * restore station in list
     *
     * @param item     {@link RecyclingStation}
     * @param position int position
     */
    public void restoreItem(RecyclingStation item, int position) {
        stations.add(position, item);
        notifyItemInserted(position);
    }

    //share location
    public void shareStationLocation(String address) {
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



    public class RecyclerHolder extends RecyclerView.ViewHolder {
        public FrameLayout frame;
        public TextView addressTextView;
        public ImageView imageView;
        public ImageButton shareButton;
        public StreetView streetView;

        public RelativeLayout viewBackground, viewForeground;


        //Constructor
        RecyclerHolder(final View itemView) {
            super(itemView);

            init(itemView);
            initListeners(itemView);
        }

        //init views
        private void init(@NonNull View view) {
            this.frame = view.findViewById(R.id.frame);
            this.addressTextView = view.findViewById(R.id.titlePlace);
            this.imageView = view.findViewById(R.id.imgPlace);
            this.shareButton = view.findViewById(R.id.imageButton);

            addressTextView.setTextColor(Color.BLACK);

            streetView = new StreetView.Builder(CleanConstants.GOOGLE_API_KEY)
                    .pitch("-0.76")
                    .heading("80.0")
                    .size("600x400")
                    .fov("90")
                    .build();
        }

        //init listeners
        private void initListeners(@NonNull final View view) {
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onButtonShareClick(v, getLayoutPosition());

                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //tinyDB.clear();
                    tinyDB.remove(CleanConstants.ADDRESS);
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                }
            });
        }

        /**
         * @return FrameLayout
         */
        FrameLayout getFrame() {
            return frame;
        }


    }
}
