package com.malivasileva.librarian.presentation.di;

import com.malivasileva.librarian.domain.repositories.LibrarianRepository;
import com.malivasileva.librarian.domain.usecases.ExitLibrarianUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModule {

    @Provides
    public ExitLibrarianUseCase provideExitLibrarianUseCase (LibrarianRepository librarianRepository) {
        return new ExitLibrarianUseCase(librarianRepository);
    }
}
