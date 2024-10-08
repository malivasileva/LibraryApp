package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.ReaderEntity;
import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import java.sql.SQLException;

public class ReaderRepositoryImpl implements ReaderRepository {

    DatabaseService databaseService;

    public ReaderRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Reader getReader(int card) {
        try {
            return mapEntityToModel(databaseService.getReader(card));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateReader(Reader reader) {
        //todo
    }

    @Override
    public void deleteReader(int card) {
        //todo
    }

    private Reader mapEntityToModel(ReaderEntity entity) {
        return new Reader(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress()
        );
    }

}
