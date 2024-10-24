package com.malivasileva.data.di;

import android.content.Context;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.SharedPrefUserStorage;
import com.malivasileva.data.UserStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Single;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {
    @Provides
    @Singleton
    public DatabaseService provideDatabaseService() {
        return new DatabaseService();
    }

    @Provides
    @Singleton
    public UserStorage provideUserStorage(@ApplicationContext Context context) {
        return new SharedPrefUserStorage(context);
    }
}
