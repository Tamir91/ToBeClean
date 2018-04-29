package app.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import storage.Preferences;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    Context provideContext() {
        return context;
    }

    @AppScope
    @Provides
    Preferences providePreferences(Context context) {
        return new Preferences(context);
    }
}
