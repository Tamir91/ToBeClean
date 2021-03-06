package storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by ty on 18/03/18.
 */

public class Preferences {
    private final static String TAG = Preferences.class.getSimpleName();
    private final static String LANGUAGE = "interfaceLanguage";
    private final static String ENGLISH = "ENGLISH";

    private final static String PLACES = "FAVORITES_PLACES";

    private static SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    /**This function write language app to memory */
    public void setLanguageApp(String s) {
        getEditor().putString(LANGUAGE, s).apply();
    }

    /**This function get language app from memory */
    public String getLanguageApp() {
        return preferences.getString(LANGUAGE, ENGLISH);
    }

    public void saveFavoritePlace(String s){
        getEditor().putString(PLACES, s).apply();
        Log.d(TAG, "saveFavoritePlace::in");
    }

    public String getFavoritePlace(){
        Log.d(TAG, "getFavoritePlace::in");
        return preferences.getString(PLACES, "adapter::not_found");
    }

    public void removeFavoritePlace(String palce) {
        getEditor().remove(PLACES).apply();

    }

}
