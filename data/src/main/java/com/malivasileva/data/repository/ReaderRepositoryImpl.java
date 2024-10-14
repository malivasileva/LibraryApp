package com.malivasileva.data.repository;

import android.util.Log;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.entities.ReaderEntity;
import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import java.sql.SQLException;

import io.reactivex.rxjava3.core.Single;

public class ReaderRepositoryImpl implements ReaderRepository {

    DatabaseService databaseService;
    UserStorage userStorage;

    public ReaderRepositoryImpl(DatabaseService databaseService, UserStorage userStorage) {
        this.databaseService = databaseService;
        this.userStorage = userStorage;
    }

    @Override
    public Single<Reader> getReaderWithId(int card) {
//        Log.d("govno-repoimpl", "card is " + card);
        return databaseService.getReader(card)
                .map(this::mapEntityToModel)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new Reader(
                            -1,
                            "Ошибка получения данных",
                            "00000000",
                            "UNKNOWN");
                });
    }

    @Override
    public Single<Reader> getReader() {
        Log.d("govno-readerRepoImpl", "in getReader");
        return databaseService.getReader(userStorage.getId())
                .map(this::mapEntityToModel)
                .onErrorReturn(
                        throwable -> {
                            throwable.printStackTrace();
                            return new Reader(
                                    -1,
                                    "Ошибка получения данных",
                                    "00000000",
                                    "UNKNOWN");
                        }
                );
    }

    @Override
    public void updateReader(Reader reader) {
        //todo
    }

    @Override
    public void deleteReader(int card) {
        //todo
    }

    private Reader mapEntityToModel(ReaderEntity entity) {
        return new Reader(
                (int) entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress()
        );
    }

}
