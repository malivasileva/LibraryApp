package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

public class GetProfileUseCase {
    ReaderRepository readerRepository;
    public GetProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    public Reader execute(int card) {
        return readerRepository.getReader(card);
    }
}
