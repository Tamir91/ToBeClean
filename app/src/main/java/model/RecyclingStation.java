package model;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class RecyclingStation {

    //In this app 6 containers the maximum in station. But this not real world.
    private static final int MAX_CONTAINERS_IN_STATION = 4;
    LatLng latLng;

    private ArrayList<RecyclingContainer> recyclingContainers;


    /**
     * Empty constructor.
     */
    public RecyclingStation() {
        recyclingContainers = new ArrayList<>();
    }


    /**
     * Second constructor.
     *
     * @param list {@link ArrayList<RecyclingContainer>}
     */
    public RecyclingStation(ArrayList<RecyclingContainer> list) {
        if (recyclingContainers.size() < MAX_CONTAINERS_IN_STATION) {
            recyclingContainers.addAll(list);
        }
    }

    /**
     * Add container to station.
     *
     * @param container {@link RecyclingContainer}
     * @return boolean
     */
    public boolean addContainer(RecyclingContainer container) {
        if (recyclingContainers.size() < MAX_CONTAINERS_IN_STATION) {
            recyclingContainers.add(container);
            return true;
        }
        return false;
    }


    /**
     * Return list of containers.
     *
     * @return ArrayList
     */
    public ArrayList<RecyclingContainer> getRecyclingContainers() {
        return recyclingContainers;
    }

    /**
     * Return number of containers in station.
     *
     * @return int
     */
    public int getNumberContainersInStation() {
        return recyclingContainers.size();
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
}
