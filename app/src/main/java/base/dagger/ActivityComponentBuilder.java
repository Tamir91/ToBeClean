package base.dagger;

import app.dagger.ActivityModule;
import app.dagger.AppComponent;

public interface ActivityComponentBuilder<C extends AppComponent, M extends ActivityModule> {

    C build();

    //ActivityComponentBuilder<C, M> module(M module);
}
