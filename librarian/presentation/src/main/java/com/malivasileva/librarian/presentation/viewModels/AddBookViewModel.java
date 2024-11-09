package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.AddBookUseCase;
import com.malivasileva.model.Book;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddBookViewModel extends ViewModel {

    private final AddBookUseCase addBookUseCase;

    MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AddBookViewModel(
            AddBookUseCase addBookUseCase
    ) {
        this.addBookUseCase = addBookUseCase;
    }

    public LiveData<String> getEventLiveData () { return eventLiveData; }

    public void addBook(Book book, Runnable onSuccessClearForm) {
        disposable.add(
                addBookUseCase.execute(book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( result -> {
                            String msg;
                            if (result) {
                                msg = "Книга создана!";
                                onSuccessClearForm.run();
                            } else {
                                msg = "Невозможно добавить книгу";
                            }
                            eventLiveData.setValue(msg);
                        }, throwable -> {
                            eventLiveData.setValue("Ошибка: " + throwable.getMessage());
                        })
        );
    }

}
