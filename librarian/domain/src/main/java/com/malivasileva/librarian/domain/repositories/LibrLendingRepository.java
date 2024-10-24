package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LibrLendingRepository {
    public Single<List<Lending>> getLendingsForBookWith(int num);
    public Single<List<Lending>> getAllCurrentLendings();
}
