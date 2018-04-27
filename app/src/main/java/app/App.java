package app;

import android.app.Application;
import android.content.Context;

/**
 * This class help create a component for ToBeClean App
 */

public class App extends Application {

    private ComponentsHolder componentsHolder;

    /**
     * @return App
     */
    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public ComponentsHolder getComponentsHolder() {
        return componentsHolder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        componentsHolder = new ComponentsHolder(this);
        componentsHolder.init();
    }
}
