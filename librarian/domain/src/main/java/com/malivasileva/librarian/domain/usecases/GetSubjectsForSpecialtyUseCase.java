package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.StudyPlanRepository;
import com.malivasileva.model.Subject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetSubjectsForSpecialtyUseCase {
    private final StudyPlanRepository studyPlanRepository;

    public GetSubjectsForSpecialtyUseCase(
            StudyPlanRepository studyPlanRepository
    ) {
        this.studyPlanRepository = studyPlanRepository;
    }

    public Single<List<Subject>> execute(int id) {
        return studyPlanRepository.getSubjectsForSpecialty(id);
    }
}
