package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.AddLendingUseCase;
import com.malivasileva.librarian.domain.usecases.GetBookWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.GetReaderWithIdUseCase;
import com.malivasileva.model.Book;
import com.malivasileva.model.Reader;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddLendingViewModel extends ViewModel {

    private final AddLendingUseCase addLendingUseCase;
    private final GetReaderWithIdUseCase getReaderWithIdUseCase;
    private final GetBookWithIdUseCase getBookWithIdUseCase;

    private MutableLiveData<Reader> readerLiveData = new MutableLiveData<>();
    private MutableLiveData<Book> bookLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AddLendingViewModel(
            AddLendingUseCase addLendingUseCase,
            GetReaderWithIdUseCase getReaderWithIdUseCase,
            GetBookWithIdUseCase getBookWithIdUseCase) {
        this.addLendingUseCase = addLendingUseCase;
        this.getReaderWithIdUseCase = getReaderWithIdUseCase;
        this.getBookWithIdUseCase = getBookWithIdUseCase;
    }

    public LiveData<Reader> getReaderLiveData() { return readerLiveData; }
    public LiveData<Book> getBookLiveData() { return bookLiveData; }
    public LiveData<String> getErrorLiveData() { return errorLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getReader(int num) {
        disposable.add(
                getReaderWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( reader -> {
                            readerLiveData.setValue(reader);
                        }, throwable -> {
                            errorLiveData.setValue("Ошибка получения данных: " + throwable.getMessage());
                        })
        );
    }

    public void getBook(int num) {
        disposable.add(
                getBookWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( book -> {
                            bookLiveData.setValue(book);
                        }, throwable -> {
                            errorLiveData.setValue("Ошибка получения данных: " + throwable.getMessage());
                        })
        );
    }

    public void addLending(int reader, int book) {
        disposable.add(
                addLendingUseCase.execute(reader, book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( result -> {
                            String msg;
                            if (result) {
                                msg = "Книга выдана!";
                            } else {
                                msg = "Невозможно добавить выдачу";
                            }
                            eventLiveData.setValue(msg);
                        }, throwable -> {
                            errorLiveData.setValue("Ошибка: " + throwable.getMessage());
                        })
        );
    }
}
