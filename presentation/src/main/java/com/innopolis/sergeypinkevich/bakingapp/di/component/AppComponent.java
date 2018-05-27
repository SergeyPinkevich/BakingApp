package com.innopolis.sergeypinkevich.bakingapp.di.component;

import com.innopolis.sergeypinkevich.bakingapp.di.module.AppModule;
import com.innopolis.sergeypinkevich.bakingapp.di.module.RepositoryModule;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.DetailActivity;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Sergey Pinkevich
 */
@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);
}
