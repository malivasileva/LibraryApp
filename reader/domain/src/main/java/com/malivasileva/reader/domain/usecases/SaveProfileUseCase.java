package com.malivasileva.reader.domain.usecases;

import com.malivasileva.model.Reader;
import com.malivasileva.reader.domain.repositories.ReaderRepository;

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
