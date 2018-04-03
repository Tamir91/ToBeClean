package model;

import android.media.Image;

import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/03/18.
 */

public class PlaceItem {
    private String placeAddress;
    private int imgID;

    /**
     * First Constructor
     */
    public PlaceItem() {
        placeAddress = "without_address";
        imgID = R.mipmap.ic_launcher_round;
    }

    /**
     * Second Constructor
     */
    public PlaceItem(String placeAddress, int imgID) {
        this.placeAddress = placeAddress;
        this.imgID = imgID;
    }

    /**
     * @return String
     */
    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    /**
     * @return int
     */
    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
}
