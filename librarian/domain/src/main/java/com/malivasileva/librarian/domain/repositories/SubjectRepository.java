package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Subject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SubjectRepository {
    Single<List<Subject>> getSubjectsFor (int specialtyNum);
}
