package model;

import com.google.android.gms.maps.model.LatLng;

import tobeclean.tobeclean.R;

/**
 * Created by tamir on 05/03/18.
 */

public class RecyclingSpot {
    private String placeAddress;
    private LatLng latLng;
    private int imgID;
    private short type;


    /**
     * First Constructor
     */
    public RecyclingSpot() {
        placeAddress = "ברל 9";
        imgID = R.mipmap.ic_launcher_round;
    }

    /**
     * Second Constructor
     */
    public RecyclingSpot(String placeAddress, int imgID) {
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

    /**
     * @return short
     */
    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
