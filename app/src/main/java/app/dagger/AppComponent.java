package app.dagger;

import app.ComponentsHolder;
import dagger.Component;
import storage.Preferences;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectComponentsHolder(ComponentsHolder componentsHolder);

    Preferences getPreferences();

    //TODO Complete the interface (module)
}
