package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Reader;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchReadersWithActiveLendingsUseCase {
    private final LibrReaderRepository readerRepository;

    public SearchReadersWithActiveLendingsUseCase(LibrReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Single<List<Reader>> execute() {
        return readerRepository.getActiveReaders();
    }
}
