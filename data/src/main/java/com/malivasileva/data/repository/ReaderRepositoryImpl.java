package com.malivasileva.data.repository;

import static io.reactivex.rxjava3.core.Completable.fromAction;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.UserStorage;
import com.malivasileva.data.entities.ReaderEntity;
import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Completable;
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
    public Single<Boolean> updateReader(Reader reader) {
        return databaseService.updateReader(mapModelToEntity(reader))
                .onErrorReturn(
                        throwable -> {
                            throwable.printStackTrace();
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> deleteReader() {
        return databaseService.deleteReader(userStorage.getId());
    }

    @Override
    public Completable exit() {
        return Completable.fromAction(() -> {
            userStorage.deleteId();
        });
    }

    private Reader mapEntityToModel(ReaderEntity entity) {
        return new Reader(
                (int) entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress()
        );
    }

    private ReaderEntity mapModelToEntity(Reader reader) {
        return new ReaderEntity(
                reader.getCard(),
                reader.getName(),
                reader.getPhone(),
                reader.getAddress());
    }

}
