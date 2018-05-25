package com.innopolis.sergeypinkevich.bakingapp.di.module;

import android.content.Context;

import com.innopolis.sergeypinkevich.bakingapp.di.scope.ApplicationContext;
import com.innopolis.sergeypinkevich.data.repository.DataProviderImpl;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Sergey Pinkevich
 */
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    DataProvider provideDataProvider(@ApplicationContext Context context) {
        return new DataProviderImpl(context);
    }
}
