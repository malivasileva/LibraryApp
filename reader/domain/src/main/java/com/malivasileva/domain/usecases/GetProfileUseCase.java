package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.repositories.ReaderRepository;

import io.reactivex.rxjava3.core.Single;

public class GetProfileUseCase {
    ReaderRepository readerRepository;
    public GetProfileUseCase(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    public Single<Reader> execute() {
        var tmp = readerRepository.getReader();
        tmp.map(reader -> {
            if (reader != null) {
                return reader.getName(); // Получаем название первой книги
            } else {
                return "No books available"; // Возвращаем строку, если список пустой
            }
        })
                .subscribe(name -> {
                    // Обработка результата, например, вывод названия книги в лог
                    System.out.println("govno profile name: " + name);
                }, throwable -> {});
                    // Обработка ошибки
        return tmp;
    }
}
