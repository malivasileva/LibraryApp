package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.StudySeries;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface StudySeriesRepository {
    Single<List<StudySeries>> getAllStudySeries();
}
