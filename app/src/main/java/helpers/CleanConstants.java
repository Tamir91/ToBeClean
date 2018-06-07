package helpers;

import android.Manifest;

public final class CleanConstants {

    /**
     * Opposite of {@link #FAILS}.
     */
    public static final boolean PASSES = true;
    /**
     * Opposite of {@link #PASSES}.
     */
    public static final boolean FAILS = false;

    /**
     * Opposite of {@link #FAILURE}.
     */
    public static final boolean SUCCESS = true;
    /**
     * Opposite of {@link #SUCCESS}.
     */
    public static final boolean FAILURE = false;
    /**
     * Default Zoom for map.
     */

    public static final short GLASS = 0;
    public static final short PLASTIC = 1;
    public static final short PAPER = 2;
    public static final short BOX = 3;
    public static final short CLOTHES = 4;
    public static final short ELECTRICAL = 5;

    public static final String GOOGLE_API_KEY = "AIzaSyC4hAj8LmqsM-3n300DRSb-jERRN_PxMfQ";
    public static final Float DEFAULT_ZOOM = 15f;
    /***/
    public static final String FINE_LOCATION_PERMISSON = Manifest.permission.ACCESS_FINE_LOCATION;
    /***/
    public static final String COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String ADDRESS = "addressKey";
    public static final String GOOGLE_MAP_ADDRESS = "https://maps.google.com/?q=";


    public static final String DISTANCE_METRIC_SYSTEM = "distanceMetricKey";
    public static final String DISTANCE_KILOMETER = "kilometer";
    public static final String DISTANCE_MILE = "mile";

    public static final String SEARCHING_VALUE = "searching_key";

}
