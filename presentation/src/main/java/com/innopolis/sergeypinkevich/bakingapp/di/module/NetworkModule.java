package com.innopolis.sergeypinkevich.bakingapp.di.module;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.gson.GsonBuilder;
import com.innopolis.sergeypinkevich.data.repository.BakingService;
import com.innopolis.sergeypinkevich.data.repository.DataProviderImpl;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
    }

    @Singleton
    @Provides
    BakingService provideApiService(Retrofit retrofit) {
        return retrofit.create(BakingService.class);
    }

    @Singleton
    @Provides
    DataProvider providerData(BakingService service) {
        return new DataProviderImpl(service);
    }
}
