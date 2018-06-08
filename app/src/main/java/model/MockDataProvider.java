package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static helpers.CleanConstants.BOX;
import static helpers.CleanConstants.CLOTHES;
import static helpers.CleanConstants.ELECTRICAL;
import static helpers.CleanConstants.GLASS;
import static helpers.CleanConstants.PAPER;
import static helpers.CleanConstants.PLASTIC;

/**
 * This class build mock station in Haifa, Neve Sheanan / Merckaz Ziv.
 */
public class MockDataProvider {


    private static final String HAIFA_ISRAEL = ", Haifa, Israel";
    private static ArrayList recyclingStationList;

    public MockDataProvider() {
        buildMockList();
    }

    public ArrayList<RecyclingStation> buildMockList() {

        if (recyclingStationList == null) {
            recyclingStationList = new ArrayList();

            RecyclingStation recyclingStation1 = new RecyclingStation();
            recyclingStation1.setAddress("Berl Katznelson 9" + HAIFA_ISRAEL);

            recyclingStation1.addContainer(new RecyclingContainer(GLASS));
            recyclingStation1.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation1.addContainer(new RecyclingContainer(PAPER));
            recyclingStation1.setLatLng(new LatLng(32.786625, 35.015429));

            recyclingStationList.add(recyclingStation1);


            RecyclingStation recyclingStation2 = new RecyclingStation();
            recyclingStation2.setAddress("Pinhas Re'em 2" + HAIFA_ISRAEL);

            recyclingStation2.addContainer(new RecyclingContainer(GLASS));
            recyclingStation2.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation2.addContainer(new RecyclingContainer(PAPER));
            recyclingStation2.addContainer(new RecyclingContainer(BOX));
            recyclingStation2.setLatLng(new LatLng(32.783594, 35.013078));

            recyclingStationList.add(recyclingStation2);


            RecyclingStation recyclingStation3 = new RecyclingStation();
            recyclingStation3.setAddress("Aba Hilel Silver 109" + HAIFA_ISRAEL);

            recyclingStation3.addContainer(new RecyclingContainer(GLASS));
            recyclingStation3.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation3.addContainer(new RecyclingContainer(PAPER));
            recyclingStation3.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation3.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation3.setLatLng(new LatLng(32.793729, 35.006725));

            recyclingStationList.add(recyclingStation3);


            RecyclingStation recyclingStation4 = new RecyclingStation();
            recyclingStation4.setAddress("Dubnov 28" + HAIFA_ISRAEL);

            recyclingStation4.addContainer(new RecyclingContainer(GLASS));
            recyclingStation4.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation4.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation4.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation4.setLatLng(new LatLng(32.777699, 35.010566));

            recyclingStationList.add(recyclingStation4);

            RecyclingStation recyclingStation5 = new RecyclingStation();
            recyclingStation5.setAddress("Yitshak Ben Zvi 18" + HAIFA_ISRAEL);

            recyclingStation5.addContainer(new RecyclingContainer(GLASS));
            recyclingStation5.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation5.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation5.addContainer(new RecyclingContainer(PAPER));
            recyclingStation5.addContainer(new RecyclingContainer(BOX));
            recyclingStation5.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation5.setLatLng(new LatLng(32.818805, 35.067627));

            recyclingStationList.add(recyclingStation5);


            RecyclingStation recyclingStation6 = new RecyclingStation();
            recyclingStation6.setAddress("Sderot Achi Eilat 11" + HAIFA_ISRAEL);

            recyclingStation6.addContainer(new RecyclingContainer(GLASS));
            recyclingStation6.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation6.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation6.addContainer(new RecyclingContainer(PAPER));
            recyclingStation6.addContainer(new RecyclingContainer(BOX));
            recyclingStation6.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation6.setLatLng(new LatLng(32.822241, 35.067870));

            recyclingStationList.add(recyclingStation6);


            RecyclingStation recyclingStation7 = new RecyclingStation();
            recyclingStation7.setAddress("HaPlugot 54" + HAIFA_ISRAEL);

            recyclingStation7.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation7.addContainer(new RecyclingContainer(PAPER));
            recyclingStation7.setLatLng(new LatLng(32.830500, 35.071320));

            recyclingStationList.add(recyclingStation7);


            RecyclingStation recyclingStation8 = new RecyclingStation();
            recyclingStation8.setAddress("Sderot HaRo'e 15" + HAIFA_ISRAEL);

            recyclingStation8.addContainer(new RecyclingContainer(GLASS));
            recyclingStation8.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation8.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation8.addContainer(new RecyclingContainer(PAPER));
            recyclingStation8.addContainer(new RecyclingContainer(BOX));
            recyclingStation8.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation8.setLatLng(new LatLng(32.833819, 35.066489));

            recyclingStationList.add(recyclingStation8);


            RecyclingStation recyclingStation9 = new RecyclingStation();
            recyclingStation9.setAddress("Sderot Deganya 33" + HAIFA_ISRAEL);

            recyclingStation9.addContainer(new RecyclingContainer(GLASS));
            recyclingStation9.addContainer(new RecyclingContainer(PLASTIC));
            recyclingStation9.addContainer(new RecyclingContainer(CLOTHES));
            recyclingStation9.addContainer(new RecyclingContainer(PAPER));
            recyclingStation9.addContainer(new RecyclingContainer(BOX));
            recyclingStation9.addContainer(new RecyclingContainer(ELECTRICAL));
            recyclingStation9.setLatLng(new LatLng(32.829837, 35.057055));

            recyclingStationList.add(recyclingStation9);
        }

        return recyclingStationList;
    }

    public ArrayList<RecyclingStation> getRecyclingStationList() {
        return recyclingStationList;
    }

}
