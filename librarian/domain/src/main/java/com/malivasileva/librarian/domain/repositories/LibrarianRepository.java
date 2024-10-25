package com.malivasileva.librarian.domain.repositories;

import io.reactivex.rxjava3.core.Completable;

public interface LibrarianRepository {
    Completable exit();
}
