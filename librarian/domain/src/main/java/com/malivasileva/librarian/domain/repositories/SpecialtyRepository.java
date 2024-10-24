package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Specialty;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SpecialtyRepository {
    Single<List<Specialty>> getAllSpecialty();
    Single<List<Specialty>> getSpecialtyFor(String query);
}
