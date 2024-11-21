package com.malivasileva.librarian.presentation.di;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.repository.LibrBookRepositoryImpl;
import com.malivasileva.data.repository.LibrLendingsRepositoryImpl;
import com.malivasileva.data.repository.LibrReaderRepositoryImpl;
import com.malivasileva.data.repository.LibrarianRepositoryImpl;
import com.malivasileva.data.repository.SpecialtyRepositoryImpl;
import com.malivasileva.data.repository.StudyPlanRepositoryImpl;
import com.malivasileva.data.repository.StudySeriesRepositoryImpl;
import com.malivasileva.data.repository.SylabusRepositoryImpl;
import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;
import com.malivasileva.librarian.domain.repositories.LibrarianRepository;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;
import com.malivasileva.librarian.domain.repositories.StudyPlanRepository;
import com.malivasileva.librarian.domain.repositories.StudySeriesRepository;
import com.malivasileva.librarian.domain.repositories.SylabusRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public LibrarianRepository provideLibrarianRepository(UserStorage userStorage) {
        return new LibrarianRepositoryImpl(userStorage);
    }

    @Provides
    @Singleton
    public LibrBookRepository provideLibrBookRepository(DatabaseService databaseService) {
        return new LibrBookRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public LibrLendingRepository provideLibrLendingRepository(DatabaseService databaseService) {
        return new LibrLendingsRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public LibrReaderRepository provideLibrReaderRepository(DatabaseService databaseService) {
        return new LibrReaderRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public SpecialtyRepository provideSpecialtyRepository(DatabaseService databaseService) {
        return new SpecialtyRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public SylabusRepository provideSylabusRepository(DatabaseService databaseService) {
        return new SylabusRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public StudySeriesRepository provideStudySeriesRepository(DatabaseService databaseService) {
        return new StudySeriesRepositoryImpl(databaseService);
    }

    @Provides
    @Singleton
    public StudyPlanRepository provideStudyPlanRepository(DatabaseService databaseService) {
        return new StudyPlanRepositoryImpl(databaseService);
    }
}
