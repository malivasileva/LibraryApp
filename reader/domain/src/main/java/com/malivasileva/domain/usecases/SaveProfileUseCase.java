package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

public class SaveProfileUseCase {
    ReaderRepository readerRepository;

    public SaveProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void execute(Reader reader) {
        readerRepository.updateReader(reader);
    }
}
