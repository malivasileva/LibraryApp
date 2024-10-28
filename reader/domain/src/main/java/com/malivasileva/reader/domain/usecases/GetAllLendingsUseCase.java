package com.malivasileva.reader.domain.usecases;

import com.malivasileva.model.Lending;
import com.malivasileva.reader.domain.repositories.ReaderLendingRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAllLendingsUseCase {
    ReaderLendingRepository lendingRepository;

    public GetAllLendingsUseCase(ReaderLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<List<Lending>> execute() {
        return lendingRepository.getAllLendings();
    }
}
