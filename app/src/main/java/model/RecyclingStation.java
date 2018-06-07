package model;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * This class may contain recycling containers of some types
 */
public class RecyclingStation {
    private static final String TAG = RecyclingStation.class.getSimpleName();

    //In this app 6 containers the maximum in station. But it's not real world.
    private static final int MAX_CONTAINERS_IN_STATION = 6;
    private LatLng latLng;
    private String address;

    private ArrayList<RecyclingContainer> containers;

    /**
     * Empty constructor.
     */
    public RecyclingStation() {
        containers = new ArrayList<>();
    }

    /**
     * Second constructor.
     *
     * @param list {@link ArrayList<RecyclingContainer>}
     */
    public RecyclingStation(ArrayList<RecyclingContainer> list) {
        if (list.size() <= MAX_CONTAINERS_IN_STATION) {
            containers.addAll(list);
        }
    }

    /**
     * Add container to station.
     *
     * @param container {@link RecyclingContainer}
     * @return boolean
     */
    public boolean addContainer(RecyclingContainer container) {
        if (getFreeSpaceInStation() > 0) {
            containers.add(container);
            return true;
        }
        return false;
    }

    /**
     * Return list of containers.
     *
     * @return ArrayList
     */
    public ArrayList<RecyclingContainer> getContainers() {
        return containers;
    }

    /**
     * Return number of containers in station.
     *
     * @return int
     */
    public int getNumberContainersInStation() {
        return containers.size();
    }

    /**
     * Getting free space in station.
     *
     * @return int
     */
    public int getFreeSpaceInStation() {
        return MAX_CONTAINERS_IN_STATION - getNumberContainersInStation();
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Log.d(TAG, "setAddress::" + address);
        this.address = address;

    }
}
