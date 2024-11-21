package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Subject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface StudyPlanRepository {
    Single<List<Subject>> getSubjectsForSpecialty(int id);
}
