package com.malivasileva.reader.domain.repositories;

import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LendingRepository {
    public Single<List<Lending>> getAllLendings();
    public Single<List<Lending>> getCurrentLendings();
}
