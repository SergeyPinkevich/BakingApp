package com.innopolis.sergeypinkevich.bakingapp.di.module;

import android.app.Application;
import android.content.Context;

import com.innopolis.sergeypinkevich.bakingapp.di.scope.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * @author Sergey Pinkevich
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @ApplicationContext
    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }
}
