package com.malivasileva.reader.domain.usecases;

import com.malivasileva.model.Lending;
import com.malivasileva.reader.domain.repositories.LendingRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCurrentLendingsUseCase {
    LendingRepository lendingRepository;

    public GetCurrentLendingsUseCase(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }
    public Single<List<Lending>> execute() {
        return lendingRepository.getCurrentLendings();
    }
}
