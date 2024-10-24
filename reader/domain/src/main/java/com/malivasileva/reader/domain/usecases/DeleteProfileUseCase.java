package com.malivasileva.reader.domain.usecases;

import com.malivasileva.reader.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Single;

public class DeleteProfileUseCase {
    private final ReaderRepository readerRepository;

    public DeleteProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Single<Boolean> execute() {
        return readerRepository.deleteReader();
    }
}
