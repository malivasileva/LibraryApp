package com.malivasileva.reader.domain.repositories;

import com.malivasileva.model.Reader;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ReaderRepository {
    public Single<Reader> getReaderWithId(int card);
    Single<Reader> getReader();

    public Single<Boolean> updateReader(Reader reader);

    public Single<Boolean> deleteReader();
    public Completable exit();
}
