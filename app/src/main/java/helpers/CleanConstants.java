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
    public static final Float DEFAULT_ZOOM = 15f;
    /***/
    public static final String FINE_LOCATION_PERMISSON = Manifest.permission.ACCESS_FINE_LOCATION;
    /***/
    public static final String COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String ADDRESS = "addressKey";

}
