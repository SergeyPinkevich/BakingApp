package com.innopolis.sergeypinkevich.bakingapp.di;

import android.app.Application;

import com.innopolis.sergeypinkevich.bakingapp.di.component.AppComponent;
import com.innopolis.sergeypinkevich.bakingapp.di.component.DaggerAppComponent;
import com.innopolis.sergeypinkevich.bakingapp.di.module.AppModule;

/**
 * @author Sergey Pinkevich
 */
public class BaseApp extends Application {

    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
