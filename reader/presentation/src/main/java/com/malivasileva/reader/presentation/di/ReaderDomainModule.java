package com.malivasileva.reader.presentation.di;

import com.malivasileva.reader.domain.repositories.BookRepository;
import com.malivasileva.reader.domain.repositories.LendingRepository;
import com.malivasileva.reader.domain.repositories.ReaderRepository;
import com.malivasileva.reader.domain.usecases.DeleteProfileUseCase;
import com.malivasileva.reader.domain.usecases.ExitUseCase;
import com.malivasileva.reader.domain.usecases.GetAllLendingsUseCase;
import com.malivasileva.reader.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.reader.domain.usecases.GetProfileUseCase;
import com.malivasileva.reader.domain.usecases.SaveProfileUseCase;
import com.malivasileva.reader.domain.usecases.SearchBookUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class ReaderDomainModule {

    @Provides
    public ExitUseCase provideExitUseCase (ReaderRepository readerRepository) {
        return new ExitUseCase(readerRepository);
    }

    @Provides
    public DeleteProfileUseCase provideDeleteProfileUseCase (ReaderRepository readerRepository) {
        return new DeleteProfileUseCase(readerRepository);
    }

    @Provides
    public GetAllLendingsUseCase provideGetAllLendingsUseCase (LendingRepository lendingRepository) {
        return new GetAllLendingsUseCase(lendingRepository);
    }

    @Provides
    public GetCurrentLendingsUseCase provideGetCurrentLendingsUseCase (LendingRepository lendingRepository) {
        return new GetCurrentLendingsUseCase(lendingRepository);
    }

    @Provides
    public GetProfileUseCase provideGetProfileUseCase (ReaderRepository readerRepository) {
        return new GetProfileUseCase(readerRepository);
    }

    @Provides
    public SaveProfileUseCase provideSaveProfileUseCase (ReaderRepository readerRepository) {
        return new SaveProfileUseCase(readerRepository);
    }

    @Provides
    public SearchBookUseCase provideSearchBookUseCase (BookRepository bookRepository) {
        return new SearchBookUseCase(bookRepository);
    }
}
