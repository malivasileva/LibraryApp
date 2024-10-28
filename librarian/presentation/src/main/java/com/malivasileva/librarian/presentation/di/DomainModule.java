package com.malivasileva.librarian.presentation.di;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;
import com.malivasileva.librarian.domain.repositories.LibrarianRepository;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;
import com.malivasileva.librarian.domain.usecases.ExitLibrarianUseCase;
import com.malivasileva.librarian.domain.usecases.GetExpiredLendingsUseCase;
import com.malivasileva.librarian.domain.usecases.SearchBooksUseCase;
import com.malivasileva.librarian.domain.usecases.SearchReaderUseCase;
import com.malivasileva.librarian.domain.usecases.SearchSpecialtyUseCase;

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

    @Provides
    public SearchBooksUseCase provideSearchBooksUseCase (LibrBookRepository bookRepository) {
        return new SearchBooksUseCase(bookRepository);
    }

    @Provides
    public SearchReaderUseCase provideSearchReaderUseCase (LibrReaderRepository readerRepository) {
        return new SearchReaderUseCase(readerRepository);
    }

    @Provides
    public SearchSpecialtyUseCase provideSearchSpecialtyUseCase (SpecialtyRepository specialtyRepository) {
        return new SearchSpecialtyUseCase(specialtyRepository);
    }

    @Provides
    public GetExpiredLendingsUseCase provideGetExpiredLendingsUseCase (LibrLendingRepository lendingRepository) {
        return new GetExpiredLendingsUseCase(lendingRepository);
    }
}
