package com.malivasileva.data.repository;

import android.util.Log;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.BookEntity;
import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class LibrBookRepositoryImpl implements LibrBookRepository {

    private final String TAG = "LibrBookRepositoryImpl";
    private final DatabaseService databaseService;

    public LibrBookRepositoryImpl (DatabaseService databaseService) {
        this.databaseService = databaseService;
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

    @Override
    public Single<Book> getBookWithId(int bookId) {
        return databaseService.getBookWithId(bookId)
                .map(this::mapEntityToModel)
                .onErrorReturn(throwable -> {
                    return new Book(
                            0,
                            "Ошибка получения данных",
                            throwable.getMessage(),
                            "",
                            "",
                            0,
                            0f,
                            0,
                            0
                    );
                });
    }

    @Override
    public Single<Boolean> updateBook(Book book) {
        return databaseService.updateBook(mapModelToEntity(book))
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> deleteBook(int id) {
        return databaseService.deleteBook(id)
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> addBook(Book book) {
        return databaseService.addBook(mapModelToEntity(book));
                /*.onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );*/
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

    private BookEntity mapModelToEntity(Book book) {
        return new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublisheAddress(),
                book.getPublisherName(),
                book.getPages(),
                book.getPrice(),
                book.getCopies(),
                book.getYear()
        );
    }
}
