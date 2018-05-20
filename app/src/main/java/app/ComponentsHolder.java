package app;

import android.content.Context;


/**
 * This class
 */
public class ComponentsHolder {

    /*private final Context context;
    *//*@Inject
    Map<Class<?>, Provider<ActivityComponentBuilder>> builders;*//*

    private Map<Class<?>, ActivityComponent> components;
    private AppComponent appComponent;


    *//**
     * @param context {@link Context}
     *                Constructor
     *//*
    public ComponentsHolder(Context context) {
        this.context = context;
    }

    *//**
     * Init component
     *//*
    public void init() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
        appComponent.injectComponentsHolder(this);
        components = new HashMap<>();
    }

    *//**
     * @return AppComponent
     *//*
    public AppComponent getAppComponent() {
        return appComponent;
    }

    *//**
     * @param cls Class<?>
     * @return ActivityComponent
     *//*
    public ActivityComponent getActivityComponent(Class<?> cls) {
        return getActivityComponent(cls, null);
    }

    *//**
     * This function
     *
     * @param cls    Class<?>
     * @param module {@link ActivityModule}
     * @return ActivityComponent
     *//*
    public AppComponent getActivityComponent(Class<?> cls, ActivityModule module) {
        AppComponent component = components.get(cls);

        if (component == null) {

           *//* ActivityComponentBuilder builder = builders.get(cls).get();
            if (module != null) {
                builder.module(module);
            }
            component = builder.build();
            components.put(cls, component);*//*
        }
        return component;
    }

    *//**
     * @param cls Class<?>
     *//*
    public void releaseActivityComponent(Class<?> cls) {
        components.put(cls, null);
    }*/
}
