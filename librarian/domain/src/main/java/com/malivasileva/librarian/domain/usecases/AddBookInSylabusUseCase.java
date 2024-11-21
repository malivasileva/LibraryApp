package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.SylabusRepository;

import io.reactivex.rxjava3.core.Single;

public class AddBookInSylabusUseCase {
    private final SylabusRepository sylabusRepository;

    public AddBookInSylabusUseCase(
            SylabusRepository sylabusRepository
    ) {
        this.sylabusRepository = sylabusRepository;
    }

    public Single<Boolean> execute(int studyPlan, int book) {
        return sylabusRepository.addBookInSylabus(studyPlan, book);
    }
}
