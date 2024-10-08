package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.BookEntity;
import com.malivasileva.domain.model.Book;
import com.malivasileva.domain.repositories.BookRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepositoryImpl implements BookRepository {
    private final DatabaseService databaseService;

    public BookRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public List<Book> getAllBooks() {
        return Collections.emptyList();
    }

    @Override
    public List<Book> getBooksFor(String query) {
        try {
            return databaseService.getBooksFor(query).stream()
                    .map(this::mapEntityToModel)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();

            List<Book> noBooksFound = new ArrayList<>();
            noBooksFound.add(new Book(
                    0,
                    "Ошибка подключения данных",
                    Arrays.toString(e.getStackTrace()),
                    "",
                    "",
                    0,
                    0f,
                    0,
                    0
            ));
            return noBooksFound;
        }
    }

    private Book mapEntityToModel(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthors(),
                entity.getAddress(),
                entity.getPublisher(),
                entity.getPages(),
                entity.getPrice(),
                entity.getCopies(),
                entity.getYear()
        );
    }
}
