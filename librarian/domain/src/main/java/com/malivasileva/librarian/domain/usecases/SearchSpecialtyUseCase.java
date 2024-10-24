package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Specialty;
import com.malivasileva.librarian.domain.repositories.SpecialtyRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchSpecialtyUseCase {
    private final SpecialtyRepository specialtyRepository;

    public SearchSpecialtyUseCase(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Single<List<Specialty>> execute(String query) {
        return specialtyRepository.getSpecialtyFor(query);
    }
}
