package helpers;

import android.view.View;

/**
 * Interface for the touch events in each item
 */
public interface OnItemTouchListener {
    /**
     * Callback invoked when the user Taps one of the RecyclerView items
     *
     * @param view     the CardView touched
     * @param position the index of the item touched in the RecyclerView
     */
    void onCardViewTap(View view, int position);


    /**
     * Callback invoked when the Share of an item is touched
     *
     * @param view     the Button touched
     * @param position the index of the item touched in the RecyclerView
     */
    void onButtonShareClick(View view, int position);
}