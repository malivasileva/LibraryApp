package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;
import com.malivasileva.model.Reader;

import io.reactivex.rxjava3.core.Single;

public class GetReaderWithIdUseCase {
    private final LibrReaderRepository readerRepository;

    public GetReaderWithIdUseCase(LibrReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Single<Reader> execute(int readerId) {
        return readerRepository.getReaderWithId(readerId);
    }
}
