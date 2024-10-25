package com.malivasileva.librarian.presentation.di;

import android.content.Context;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.SharedPrefUserStorage;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.repository.LibrarianRepositoryImpl;
import com.malivasileva.librarian.domain.repositories.LibrarianRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public LibrarianRepository provideLibrarianRepository(UserStorage userStorage) {
        return new LibrarianRepositoryImpl(userStorage);
    }
}
