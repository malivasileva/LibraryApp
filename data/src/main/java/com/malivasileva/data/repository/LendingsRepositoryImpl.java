package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.entities.LendingEntity;
import com.malivasileva.domain.model.Lending;
import com.malivasileva.domain.repositories.LendingRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class LendingsRepositoryImpl implements LendingRepository {

    DatabaseService databaseService;
    UserStorage userStorage;

    public LendingsRepositoryImpl(DatabaseService databaseService, UserStorage userStorage) {
        this.databaseService = databaseService;
        this.userStorage = userStorage;
    }

    @Override
    public Single<List<Lending>> getAllLendings() {
        return databaseService.getLendingsFor(userStorage.getId())
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
    public Single<List<Lending>> getCurrentLendings() {
        return databaseService.getCurrentLendingsFor(userStorage.getId())
                .map( entities -> entities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList()))
                .onErrorReturn( throwable -> {
                    throwable.printStackTrace();
                    return Collections.emptyList();
                });
    }

    private Lending mapEntityToModel(LendingEntity entity) {
        return new Lending(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthors(),
                entity.getStartDate(),
                entity.getRequiredDate(),
                entity.getReturnedDate());
    }
}
