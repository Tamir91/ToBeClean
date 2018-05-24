package model;

import com.google.android.gms.maps.model.LatLng;

import tobeclean.tobeclean.R;

/**
 * `
 * Created by tamir on 05/03/18.
 */

public class RecyclingContainer {
    private static final short GLASS = 0;
    private static final short PLASTIC = 1;
    private static final short PAPER = 2;
    private static final short BOX = 3;

    private String placeAddress;
    private LatLng latLng;//map API can help me with it. And I want save it in to DB.
    private int imgID;//google street viewing. I'm a dreamer.
    private int iconID;
    private int type;


    /**
     * First Constructor
     * @param placeAddress {@link String}
     * @param type int
     */
//    public RecyclingContainer(String placeAddress, int type) {
//        this.placeAddress = "";
//        imgID = R.mipmap.ic_launcher_round;
//    }

    /**
     * Second Constructor
     */
    public RecyclingContainer(String placeAddress/*, int imgID*/, int type) {
        this.placeAddress = placeAddress;
        this.imgID = R.mipmap.ic_launcher_round;
        this.type = type;

        setIconID();
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
     * @return int
     */
    public int getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID() {
        switch (type) {
            case GLASS: {
                iconID = R.drawable.icon_glass;
                break;
            }
            case PLASTIC: {
                iconID = R.drawable.icon_plastic;
                break;
            }
            case PAPER: {
                iconID = R.drawable.icon_paper;
                break;
            }
            case BOX: {
                iconID = R.drawable.icon_box;
                break;
            }
            default: {
                break;
            }

        }
    }
}
