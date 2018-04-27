package app;

import android.content.Context;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import base.dagger.ActivityComponentBuilder;

public class ComponentsHolder {

    private final Context context;

    @Inject
    Map<Class<?>, Provider<ActivityComponentBuilder>> builders;

    public ComponentsHolder(Context context) {
        this.context = context;
    }

    public void init() {
    }
}
