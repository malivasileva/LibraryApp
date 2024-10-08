package com.malivasileva.domain.usecases;

import com.malivasileva.domain.repositories.ReaderRepository;

public class DeleteProfileUseCase {
    private final ReaderRepository readerRepository;

    public DeleteProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void execute(int card) {
        readerRepository.deleteReader(card);
    }
}
