package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.LendingEntity;
import com.malivasileva.domain.model.Lending;
import com.malivasileva.domain.repositories.LendingRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LendingsRepositoryImpl implements LendingRepository {

    private final DatabaseService databaseService;

    public LendingsRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public List<Lending> getAllLendingsFor(int readerId) {
        try {
            return databaseService.getLendingsFor(readerId).stream()
                    .map(this::mapEntityToModel)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Lending> getCurrentLendingsFor(int readerId) {
        return Collections.emptyList(); //todo
    }

    private Lending mapEntityToModel(LendingEntity entity) {
        return new Lending(
                entity.getId(),
                entity.getBookId(),
                entity.getReaderId(),
                entity.getStartDate(),
                entity.getRequiredDate(),
                entity.getReturnedDate());
    }
}
