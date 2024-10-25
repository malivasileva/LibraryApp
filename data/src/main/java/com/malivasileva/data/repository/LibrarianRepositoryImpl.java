package com.malivasileva.data.repository;

import com.malivasileva.data.UserStorage;
import com.malivasileva.librarian.domain.repositories.LibrarianRepository;

import io.reactivex.rxjava3.core.Completable;

public class LibrarianRepositoryImpl implements LibrarianRepository {
    UserStorage userStorage;

    public LibrarianRepositoryImpl (UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Completable exit() {
        return Completable.fromAction(() -> {
                userStorage.deleteId();
        });
    }
}
