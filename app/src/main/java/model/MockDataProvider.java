package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * This class build mock station in Haifa, Neve Sheanan / Merckaz Ziv.
 */
public class MockDataProvider {
    private static final String ADDRESS = ", Haifa, Israel";
    private static ArrayList recyclingStationList;

    public MockDataProvider() {
        buildMockList();
    }

    public ArrayList<RecyclingStation> buildMockList() {

        if (recyclingStationList == null) {
            recyclingStationList = new ArrayList();

            RecyclingStation recyclingStation1 = new RecyclingStation();

            recyclingStation1.addContainer(new RecyclingContainer("Berl Katznelson 9" + ADDRESS, 0));
            recyclingStation1.addContainer(new RecyclingContainer("Berl Katznelson 9" + ADDRESS, 1));
            recyclingStation1.addContainer(new RecyclingContainer("Berl Katznelson 9" + ADDRESS, 2));
            recyclingStation1.setLatLng(new LatLng(32.786625, 35.015429));

            recyclingStationList.add(recyclingStation1);


            RecyclingStation recyclingStation2 = new RecyclingStation();

            recyclingStation2.addContainer(new RecyclingContainer(ADDRESS + "Pinhas Re'em 2", 0));
            recyclingStation2.addContainer(new RecyclingContainer(ADDRESS + "Pinhas Re'em 2", 1));
            recyclingStation2.addContainer(new RecyclingContainer(ADDRESS + "Pinhas Re'em 2", 2));
            recyclingStation2.addContainer(new RecyclingContainer(ADDRESS + "Pinhas Re'em 2", 3));
            recyclingStation2.setLatLng(new LatLng(32.783594, 35.013078));

            recyclingStationList.add(recyclingStation2);

//
//            RecyclingStation recyclingStation3 = new RecyclingStation();
//            recyclingStation3.addContainer(new RecyclingContainer(ADDRESS + "Berel 9", 0));
//            recyclingStationList.add(recyclingStation3);
//
//            RecyclingStation recyclingStation4 = new RecyclingStation();
//            recyclingStation4.addContainer(new RecyclingContainer(ADDRESS + "Berel 9", 0));
//            recyclingStationList.add(recyclingStation4);

        }

        return recyclingStationList;
    }

    public ArrayList<RecyclingStation> getRecyclingStationList() {
        return recyclingStationList;
    }

}
