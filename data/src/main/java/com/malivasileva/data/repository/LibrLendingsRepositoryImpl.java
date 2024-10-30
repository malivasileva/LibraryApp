package com.malivasileva.data.repository;

import android.util.Log;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.LendingEntity;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class LibrLendingsRepositoryImpl implements LibrLendingRepository {

    private final DatabaseService databaseService;
    private final String TAG = "LibrLendingsRepositoryImpl";

    public LibrLendingsRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Single<List<Lending>> getLendingsForBookWith(int num) {
        return null;
    }

    @Override
    public Single<List<Lending>> getAllCurrentLendings() {

        return databaseService.getAllCurrentLendings()
                .map( entities -> entities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList())
                )
                .onErrorReturn( throwable -> {
                    throwable.printStackTrace();
                    return Collections.emptyList();
                });
    }

    @Override
    public Single<Lending> getLendingWithId(int lendingId) {
        return databaseService.getLendingWitId(lendingId)
                .map(this::mapEntityToModel)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new Lending(
                            -1,
                            0,
                            "name",
                            0, "title", "authors", null, null, null);
                });
    }

    @Override
    public Single<Boolean> updateLending(Lending lending) {
        return databaseService.updateLending(mapModelToEntity(lending))
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> returnBook(int lendingId) {
        return databaseService.returnBook(lendingId)
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> expandReturnDate(int lendingId) {
        return databaseService.expendReturnDate(lendingId)
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    private Lending mapEntityToModel(LendingEntity entity) {
        return new Lending(
                entity.getId(),
                entity.getReaderId(),
                entity.getReaderName(),
                entity.getBookId(),
                entity.getTitle(),
                entity.getAuthors(),
                entity.getStartDate(),
                entity.getRequiredDate(),
                entity.getReturnedDate());
    }

    private LendingEntity mapModelToEntity(Lending lending) {
        return new LendingEntity(
                lending.getId(),
                lending.getReaderId(),
                lending.getReaderName(),
                lending.getBookId(),
                lending.getTitle(),
                lending.getAuthors(),
                lending.getLendDate(),
                lending.getRequiredDate(),
                lending.getReturnDate());
    }
}
