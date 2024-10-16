package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Single;

public class SaveProfileUseCase {
    ReaderRepository readerRepository;

    public SaveProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Single<Boolean> execute(Reader reader) {
        return readerRepository.updateReader(reader);
    }
}
