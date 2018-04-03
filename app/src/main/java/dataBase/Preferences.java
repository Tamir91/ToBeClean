package dataBase;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by ty on 18/03/18.
 */

public class Preferences {
    private final static String TAG = "ToBeClean";
    private final static String LANGUAGE = "interfaceLanguage";
    private final static String ENGLISH = "ENGLISH";

    private static SharedPreferences preferences;

    private static SharedPreferences getPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TAG, 0);
        }

        return preferences;
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    /*This function write language app to memory */
    public static void setLanguageApp(Context context, String s) {
        getEditor(context).putString(LANGUAGE, s);
    }

    /*This function get language app from memory */
    public static String getLanguageApp(Context context, String data) {
        return getPreferences(context).getString(data, ENGLISH);
    }

}
