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

            recyclingStation2.addContainer(new RecyclingContainer( "Pinhas Re'em 2" + ADDRESS , 0));
            recyclingStation2.addContainer(new RecyclingContainer( "Pinhas Re'em 2" + ADDRESS , 1));
            recyclingStation2.addContainer(new RecyclingContainer( "Pinhas Re'em 2" + ADDRESS , 2));
            recyclingStation2.addContainer(new RecyclingContainer( "Pinhas Re'em 2" + ADDRESS , 3));
            recyclingStation2.setLatLng(new LatLng(32.783594, 35.013078));

            recyclingStationList.add(recyclingStation2);
        }

        return recyclingStationList;
    }

    public ArrayList<RecyclingStation> getRecyclingStationList() {
        return recyclingStationList;
    }

}
