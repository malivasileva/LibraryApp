package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Book;
import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class LibrLendingsRepositoryImpl implements LibrLendingRepository {

    private final DatabaseService databaseService;

    public LibrLendingsRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Single<List<Lending>> getLendingsForBookWith(int num) {
        return null;
    }

    @Override
    public Single<List<Lending>> getAllCurrentLendings() {
        return null;
    }

    @Override
    public Single<List<Lending>> getExpiredLendings() {
        return null;
    }
}
