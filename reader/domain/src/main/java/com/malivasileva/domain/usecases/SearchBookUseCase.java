package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Book;
import com.malivasileva.domain.repositories.BookRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchBookUseCase {
    BookRepository bookRepository;

    public SearchBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<List<Book>> execute(String query) {
        /*var tmp = bookRepository.getBooksFor(query);
        tmp.map(books -> {
            if (books != null && !books.isEmpty()) {
                return books.get(0).getTitle(); // Получаем название первой книги
            } else {
                return "No books available"; // Возвращаем строку, если список пустой
            }
        })
                .subscribe(title -> {
                    // Обработка результата, например, вывод названия книги в лог
                    System.out.println("First book title: " + title);
                }, throwable -> {
                    // Обработка ошибки
                    throwable.printStackTrace();
                });*/
        return bookRepository.getBooksFor(query);
    }
}
