package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Sylabus;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SylabusRepository {
    Single<List<Sylabus>> getSylabusFor(int specialtyNum);
    Single<Boolean> addBookInSylabus(int studyPlan, int book);
    Single<Boolean> deleteBookFromSylabus(int studyPlan, int book);
}
