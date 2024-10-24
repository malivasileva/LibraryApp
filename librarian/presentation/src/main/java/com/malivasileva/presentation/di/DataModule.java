package com.malivasileva.presentation.di;

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

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

}
