package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;

import io.reactivex.rxjava3.core.Single;

public class AddLendingUseCase {

    private final LibrLendingRepository lendingRepository;

    public AddLendingUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<Boolean> execute(int cardNum, int bookNum) {
        return lendingRepository.addLending(cardNum, bookNum);
    }
}
