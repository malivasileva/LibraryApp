package com.malivasileva.data.repository;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.BookEntity;
import com.malivasileva.domain.model.Book;
import com.malivasileva.domain.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

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
    public Single<List<Book>> getBooksFor(String query) {
        return databaseService.getBooksFor(query)
                .map(bookEntities -> bookEntities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList())
                )
                .onErrorReturn(throwable -> {
//                    throwable.printStackTrace();
                    List<Book> noBooksFound = new ArrayList<>();
                    noBooksFound.add(new Book(
                            0,
                            "Ошибка подключения данных",
                            throwable.getMessage(),
                            //Arrays.toString(throwable.getStackTrace()),
                            "",
                            "",
                            0,
                            0f,
                            0,
                            0
                    ));
                    return noBooksFound;
                });
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
