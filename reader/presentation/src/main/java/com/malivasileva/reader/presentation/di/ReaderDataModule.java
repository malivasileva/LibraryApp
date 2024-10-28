package com.malivasileva.reader.presentation.di;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.repository.BookRepositoryImpl;
import com.malivasileva.data.repository.ReaderRepositoryImpl;
import com.malivasileva.data.repository.LendingsRepositoryImpl;
import com.malivasileva.reader.domain.repositories.ReaderBookRepository;
import com.malivasileva.reader.domain.repositories.ReaderLendingRepository;
import com.malivasileva.reader.domain.repositories.ReaderRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ReaderDataModule {

    @Provides
    @Singleton
    public ReaderRepository provideReaderRepository(DatabaseService databaseService, UserStorage userStorage) {
        return new ReaderRepositoryImpl(databaseService, userStorage);
    }

    @Provides
    @Singleton
    public ReaderBookRepository provideBookRepository(DatabaseService databaseService) {
        return new BookRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public ReaderLendingRepository provideLendingRepository(DatabaseService databaseService, UserStorage userStorage) {
        return new LendingsRepositoryImpl(databaseService, userStorage);
    }
}
