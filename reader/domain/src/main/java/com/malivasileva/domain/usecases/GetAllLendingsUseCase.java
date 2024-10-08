package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Lending;
import com.malivasileva.domain.repositories.LendingRepository;

import java.util.List;

public class GetAllLendingsUseCase {
    LendingRepository lendingRepository;

    public GetAllLendingsUseCase(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public List<Lending> execute(int readerId) {
        return lendingRepository.getAllLendingsFor(readerId);
    }
}
