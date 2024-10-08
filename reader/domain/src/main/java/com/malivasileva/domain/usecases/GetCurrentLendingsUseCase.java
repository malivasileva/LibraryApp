package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Lending;
import com.malivasileva.domain.repositories.LendingRepository;

import java.util.List;

public class GetCurrentLendingsUseCase {
    LendingRepository lendingRepository;

    public GetCurrentLendingsUseCase(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }
    public List<Lending> execute(int readerId) {
        return lendingRepository.getCurrentLendingsFor(readerId);
    }
}
