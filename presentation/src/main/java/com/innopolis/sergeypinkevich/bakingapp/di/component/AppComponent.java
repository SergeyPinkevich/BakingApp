package com.innopolis.sergeypinkevich.bakingapp.di.component;

import com.innopolis.sergeypinkevich.bakingapp.di.module.AppModule;
import com.innopolis.sergeypinkevich.bakingapp.di.module.NetworkModule;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.DetailActivity;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.content.StepDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.list.RecipeDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Sergey Pinkevich
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);

    void inject(RecipeDetailFragment fragment);

    void inject(StepDetailFragment fragment);
}
