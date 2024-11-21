package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.SubjectEntity;
import com.malivasileva.librarian.domain.repositories.StudyPlanRepository;
import com.malivasileva.model.Subject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class StudyPlanRepositoryImpl implements StudyPlanRepository {
    private final DatabaseService databaseService;

    public StudyPlanRepositoryImpl(
            DatabaseService databaseService
    ) {
        this.databaseService = databaseService;
    }
    @Override
    public Single<List<Subject>> getSubjectsForSpecialty(int id) {
        return databaseService.getSubjectsForSpecialty(id)
                .map(subjectEntities -> subjectEntities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList()))
                .onErrorReturn( throwable -> {
                    return Collections.emptyList();
                });
    }

    private Subject mapEntityToModel(SubjectEntity entity) {
        return new Subject(
                entity.getNum(),
                entity.getName()
        );
    }
}
