package map.mvp;

import java.util.ArrayList;

import model.MockDataProvider;
import model.RecyclingContainer;
import model.RecyclingStation;

/**
 * This temple class created for working with mock data
 */
public class MapModel {

    private static ArrayList<RecyclingStation> stationList;
    private static MockDataProvider mockDataProvider;

    /**
     * Constructor
     */
    public MapModel() {
        if (mockDataProvider == null) {
            mockDataProvider = new MockDataProvider();
            stationList = mockDataProvider.getRecyclingStationList();
        }
    }

    /**
     * Return station list
     *
     * @return ArrayList
     */
    public ArrayList<RecyclingStation> getStationList() {
        return stationList;
    }

    /**
     * Return mock class object
     *
     * @return MockDataProvider
     */
    public static MockDataProvider getMockDataProvider() {
        return mockDataProvider;
    }
}
