package com.malivasileva.domain.repositories;

import com.malivasileva.domain.model.Reader;

import java.sql.SQLException;

public interface ReaderRepository {
    public Reader getReader(int card);

    public void updateReader(Reader reader);

    public void deleteReader(int card);
}
