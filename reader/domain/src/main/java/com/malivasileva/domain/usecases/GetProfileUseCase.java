package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Single;

public class GetProfileUseCase {
    ReaderRepository readerRepository;
    public GetProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    public Single<Reader> execute() {
        return readerRepository.getReader();
    }
}
