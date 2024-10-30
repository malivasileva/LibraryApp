package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.SpecialtyEntity;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;
import com.malivasileva.model.Book;
import com.malivasileva.model.Specialty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final DatabaseService databaseService;

    public SpecialtyRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Single<List<Specialty>> getAllSpecialty() {
        return databaseService.getAllSpecialties()
                .map(specialtyEntities -> specialtyEntities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList()))
                .onErrorReturn(throwable -> {
                    List<Specialty> noSpecialtiesFound = new ArrayList<>();
                    noSpecialtiesFound.add(new Specialty(
                            0,
                            throwable.getMessage()
                    ));
                    return noSpecialtiesFound;
                });
    }

    @Override
    public Single<List<Specialty>> getSpecialtyFor(String query) {
        return databaseService.getSpecialtyFor(query)
                .map(specialtyEntities -> specialtyEntities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList()))
                .onErrorReturn(throwable -> {
                    List<Specialty> noSpecialtiesFound = new ArrayList<>();
                    noSpecialtiesFound.add(new Specialty(
                            0,
                            throwable.getMessage()
                    ));
                    return noSpecialtiesFound;
                });
    }

    private Specialty mapEntityToModel(SpecialtyEntity entity) {
        return new Specialty(
                entity.getNum(),
                entity.getName()
        );
    }
}
