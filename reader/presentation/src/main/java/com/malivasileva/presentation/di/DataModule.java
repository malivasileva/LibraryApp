package com.malivasileva.presentation.di;

import android.content.Context;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.SharedPrefUserStorage;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.repository.BookRepositoryImpl;
import com.malivasileva.data.repository.ReaderRepositoryImpl;
import com.malivasileva.data.repository.LendingsRepositoryImpl;
import com.malivasileva.domain.repositories.BookRepository;
import com.malivasileva.domain.repositories.LendingRepository;
import com.malivasileva.domain.repositories.ReaderRepository;

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
    public ReaderRepository provideReaderRepository(DatabaseService databaseService, UserStorage userStorage) {
        return new ReaderRepositoryImpl(databaseService, userStorage);
    }

    @Provides
    @Singleton
    public BookRepository provideBookRepository(DatabaseService databaseService) {
        return new BookRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public LendingRepository provideLendingRepository(DatabaseService databaseService, UserStorage userStorage) {
        return new LendingsRepositoryImpl(databaseService, userStorage);
    }

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
