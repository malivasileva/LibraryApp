package com.malivasileva.librarian.presentation.di;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;
import com.malivasileva.librarian.domain.repositories.LibrarianRepository;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;
import com.malivasileva.librarian.domain.usecases.AddLendingUseCase;
import com.malivasileva.librarian.domain.usecases.DeleteBookUseCase;
import com.malivasileva.librarian.domain.usecases.ExitLibrarianUseCase;
import com.malivasileva.librarian.domain.usecases.ExpandReturnDateUseCase;
import com.malivasileva.librarian.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.librarian.domain.usecases.GetAllSpecialtiesUseCase;
import com.malivasileva.librarian.domain.usecases.GetBookWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.GetLendingWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.GetReaderWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.GetReadersWithActiveLendingsUseCase;
import com.malivasileva.librarian.domain.usecases.ReturnBookUseCase;
import com.malivasileva.librarian.domain.usecases.SearchBooksUseCase;
import com.malivasileva.librarian.domain.usecases.SearchReaderUseCase;
import com.malivasileva.librarian.domain.usecases.SearchSpecialtyUseCase;
import com.malivasileva.librarian.domain.usecases.UpdateBookUseCase;
import com.malivasileva.librarian.domain.usecases.UpdateLendingUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

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
    public GetAllSpecialtiesUseCase provideGetAllSpecialtiesUseCase (SpecialtyRepository specialtyRepository) {
        return new GetAllSpecialtiesUseCase(specialtyRepository);
    }

    @Provides
    public GetCurrentLendingsUseCase provideGetAllCurrentLendingsUseCase (LibrLendingRepository lendingRepository) {
        return new GetCurrentLendingsUseCase(lendingRepository);
    }

    @Provides
    public GetReadersWithActiveLendingsUseCase provideGetReadersWithActiveLendingsUseCase (LibrReaderRepository readerRepository) {
        return new GetReadersWithActiveLendingsUseCase(readerRepository);
    }

    @Provides
    public GetLendingWithIdUseCase provideGetLendingWithIdUseCase(LibrLendingRepository lendingRepository) {
        return new GetLendingWithIdUseCase(lendingRepository);
    }

    @Provides
    public UpdateLendingUseCase provideUpdateLendingUseCase(LibrLendingRepository lendingRepository) {
        return new UpdateLendingUseCase(lendingRepository);
    }

    @Provides
    public ReturnBookUseCase provideReturnBookUseCase (LibrLendingRepository lendingRepository) {
        return new ReturnBookUseCase(lendingRepository);
    }

    @Provides
    public ExpandReturnDateUseCase provideExpandReturnDateUseCase (LibrLendingRepository lendingRepository) {
        return new ExpandReturnDateUseCase(lendingRepository);
    }

    @Provides
    public GetBookWithIdUseCase provideGetBookWithIdUseCase (LibrBookRepository bookRepository) {
        return new GetBookWithIdUseCase(bookRepository);
    }

    @Provides
    public GetReaderWithIdUseCase provideGetReaderWithIdUseCase (LibrReaderRepository readerRepository) {
        return new GetReaderWithIdUseCase(readerRepository);
    }

    @Provides
    public AddLendingUseCase provideAddLendingUseCase (LibrLendingRepository lendingRepository) {
        return new AddLendingUseCase(lendingRepository);
    }

    @Provides
    public UpdateBookUseCase provideUpdateBookUseCase (LibrBookRepository bookRepository) {
        return new UpdateBookUseCase(bookRepository);
    }

    @Provides
    public DeleteBookUseCase provideDeleteBookUseCase (LibrBookRepository bookRepository) {
        return new DeleteBookUseCase(bookRepository);
    }
}
