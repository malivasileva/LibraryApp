package com.malivasileva.domain.usecases;

import com.malivasileva.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Completable;

public class ExitUseCase {
    public final ReaderRepository readerRepository;

    public ExitUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Completable execute() {
        return readerRepository.exit();
    }
}
