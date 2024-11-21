package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.StudySeriesRepository;
import com.malivasileva.model.StudySeries;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetStudySeriesUseCase {
    private final StudySeriesRepository studySeriesRepository;

    public GetStudySeriesUseCase (
            StudySeriesRepository studySeriesRepository
    ) {
        this.studySeriesRepository = studySeriesRepository;
    }

    public Single<List<StudySeries>> execute() {
        return studySeriesRepository.getAllStudySeries();
    }
}
