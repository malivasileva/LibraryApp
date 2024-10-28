package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;
import com.malivasileva.model.Specialty;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final DatabaseService databaseService;

    public SpecialtyRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Single<List<Specialty>> getAllSpecialty() {
        return null;
    }

    @Override
    public Single<List<Specialty>> getSpecialtyFor(String query) {
        return null;
    }
}
