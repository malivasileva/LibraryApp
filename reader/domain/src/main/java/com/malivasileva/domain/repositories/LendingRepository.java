package com.malivasileva.domain.repositories;

import com.malivasileva.domain.model.Lending;

import java.sql.SQLException;
import java.util.List;

public interface LendingRepository {
    public List<Lending> getAllLendingsFor(int readerId);
    public List<Lending> getCurrentLendingsFor(int readerId);
}
