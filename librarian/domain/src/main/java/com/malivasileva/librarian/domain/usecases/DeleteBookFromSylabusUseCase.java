package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.SylabusRepository;

import io.reactivex.rxjava3.core.Single;

public class DeleteBookFromSylabusUseCase {
    private final SylabusRepository sylabusRepository;

    public DeleteBookFromSylabusUseCase(
            SylabusRepository sylabusRepository
    ) {
        this.sylabusRepository = sylabusRepository;
    }

    public Single<Boolean> execute(int studyPlan, int book) {
        return sylabusRepository.deleteBookFromSylabus(studyPlan, book);
    }
}
