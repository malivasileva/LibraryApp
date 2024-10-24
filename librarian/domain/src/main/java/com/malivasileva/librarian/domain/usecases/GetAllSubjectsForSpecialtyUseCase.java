package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Subject;
import com.malivasileva.librarian.domain.repositories.SubjectRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAllSubjectsForSpecialtyUseCase {
    private final SubjectRepository subjectRepository;

    public GetAllSubjectsForSpecialtyUseCase(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Single<List<Subject>> execute(int specialtyNum) {
        return subjectRepository.getSubjectsFor(specialtyNum);
    }
}
