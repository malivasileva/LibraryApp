package com.malivasileva.domain.usecases;

import com.malivasileva.domain.repositories.ReaderRepository;

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
