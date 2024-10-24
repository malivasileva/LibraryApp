package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Reader;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LibrReaderRepository {
    Single<Reader> getReaderWithId(int card); // ???
    Single<List<Reader>> getAllReaders();

    Single<Boolean> deleteReader();
    Single<List<Reader>> getActiveReaders();
    Single<List<Reader>> getReadersFor(String query);
}
