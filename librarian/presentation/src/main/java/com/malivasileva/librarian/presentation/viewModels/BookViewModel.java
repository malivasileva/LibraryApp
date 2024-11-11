package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.DeleteBookUseCase;
import com.malivasileva.librarian.domain.usecases.GetBookWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.UpdateBookUseCase;
import com.malivasileva.model.Book;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class BookViewModel extends ViewModel {

    private final GetBookWithIdUseCase getBookWithIdUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    private final MutableLiveData<Book> bookLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public BookViewModel(
            GetBookWithIdUseCase getBookWithIdUseCase,
            UpdateBookUseCase updateBookUseCase,
            DeleteBookUseCase deleteBookUseCase
    ) {
        this.getBookWithIdUseCase = getBookWithIdUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
    }

    public LiveData<Book> getBookLiveData() { return bookLiveData; }

    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getBook(int num) {
        disposable.add(
                getBookWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                book -> {
                                    bookLiveData.setValue(book);
                                },
                                throwable -> {
                                    eventLiveData.setValue(throwable.getMessage());
                                }
                        )
        );
    }

    public void updateBook(
            String title,
            String authors,
            String address,
            String publisher,
            int page,
            float price,
            int copy,
            int year
    ) {
        disposable.add(
                updateBookUseCase.execute(new Book (
                        bookLiveData.getValue().getId(),
                        title,
                        authors,
                        address,
                        publisher,
                        page,
                        price,
                        copy,
                        year
                ))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( result -> {
                            String msg;
                            if (result) {
                                msg = "Изменения сохранены";
                            } else {
                                msg = "Произошла ошибка";
                            }
                            eventLiveData.setValue(msg);
                        })
        );
    }

    public void deleteBook(int id) {
        disposable.add(
                deleteBookUseCase.execute(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( result -> {
                            String msg;
                            if (result) {
                                msg = "Изменения сохранены";
                            } else {
                                msg = "Произошла ошибка";
                            }
                            eventLiveData.setValue(msg);
                        } )
        );
    }
}
