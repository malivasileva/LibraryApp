package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.ReaderEntity;
import com.malivasileva.librarian.domain.repositories.LibrReaderRepository;
import com.malivasileva.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class LibrReaderRepositoryImpl implements LibrReaderRepository {

    private final DatabaseService databaseService;

    public LibrReaderRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // hz
    @Override
    public Single<Reader> getReaderWithId(int card) {
        return null;
    }

    //todo
    @Override
    public Single<List<Reader>> getActiveReaders() {
        return null;
    }

    //todo
    @Override
    public Single<List<Reader>> getReadersFor(String query) {
        return databaseService.getReadersFor(query)
                .map( readerEntities -> readerEntities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList())
                )
                .onErrorReturn(throwable -> {
                    List<Reader> noReadersFound = new ArrayList<>();
                    noReadersFound.add(new Reader(
                            0,
                            "Ошибка подключения данных",
                            throwable.getMessage(),
                            "")
                    );
                    return noReadersFound;
                });
    }

    private Reader mapEntityToModel(ReaderEntity entity) {
        return new Reader(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress()
        );
    }
}
