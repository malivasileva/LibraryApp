package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.SylabusRepository;
import com.malivasileva.model.Sylabus;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetSylabusForSpecialtyUseCase {
    private final SylabusRepository sylabusRepository;

    public GetSylabusForSpecialtyUseCase(SylabusRepository sylabusRepository) {
        this.sylabusRepository = sylabusRepository;
    }

    public Single<List<Sylabus>> execute(int specialtyNum) {
        return sylabusRepository.getSylabusFor(specialtyNum);
    }
}
