package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Specialty;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAllSpecialtiesUseCase {
    private final SpecialtyRepository specialtyRepository;

    public GetAllSpecialtiesUseCase(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Single<List<Specialty>> execute() {
        return specialtyRepository.getAllSpecialty();
    }
}
