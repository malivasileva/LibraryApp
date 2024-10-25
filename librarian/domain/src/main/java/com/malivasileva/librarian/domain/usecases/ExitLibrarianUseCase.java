package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrarianRepository;

import io.reactivex.rxjava3.core.Completable;

public class ExitLibrarianUseCase {
    private final LibrarianRepository librarianRepository;

    public ExitLibrarianUseCase(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public Completable execute() {
        return librarianRepository.exit();
    }
}
