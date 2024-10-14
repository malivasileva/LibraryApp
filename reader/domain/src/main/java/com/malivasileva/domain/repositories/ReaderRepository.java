package com.malivasileva.domain.repositories;

import com.malivasileva.domain.model.Reader;

import io.reactivex.rxjava3.core.Single;

public interface ReaderRepository {
    public Single<Reader> getReaderWithId(int card);
    Single<Reader> getReader();

    public void updateReader(Reader reader);

    public void deleteReader(int card);
}
