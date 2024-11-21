package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.StudySeriesEntity;
import com.malivasileva.librarian.domain.repositories.StudySeriesRepository;
import com.malivasileva.model.StudySeries;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class StudySeriesRepositoryImpl implements StudySeriesRepository {
    DatabaseService databaseService;

    public StudySeriesRepositoryImpl( DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    @Override
    public Single<List<StudySeries>> getAllStudySeries() {
        return databaseService.getStudySeries()
                .map( entities -> entities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList())
                )
                .onErrorReturn( throwable -> {
                    return Collections.emptyList();
                });
    }

    private StudySeries mapEntityToModel(StudySeriesEntity entity) {
        return new StudySeries(
                entity.getNum(),
                entity.getName()
        );
    }
}
