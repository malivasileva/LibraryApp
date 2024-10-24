package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Reader;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchReaderUseCase {
    private final LibrReaderRepository readerRepository;

    public SearchReaderUseCase(LibrReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Single<List<Reader>> execute(String query) {
        return readerRepository.getReadersFor(query);
    }
}
