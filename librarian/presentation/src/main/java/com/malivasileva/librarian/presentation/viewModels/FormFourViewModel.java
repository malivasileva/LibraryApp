package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.GetBooksForSpecialtyAndSeriesUseCase;
import com.malivasileva.model.Book;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FormFourViewModel extends ViewModel {
    private final GetBooksForSpecialtyAndSeriesUseCase getBooksForSpecialtyAndSeriesUseCase;

    private MutableLiveData<List<Book>> bookLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public FormFourViewModel (
            GetBooksForSpecialtyAndSeriesUseCase getBooksForSpecialtyAndSeriesUseCase
    ) {
        this.getBooksForSpecialtyAndSeriesUseCase = getBooksForSpecialtyAndSeriesUseCase;
    }

    public LiveData<List<Book>> getBookLiveData() { return bookLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getBooks(int specialty, int series) {
        disposable.add(
                getBooksForSpecialtyAndSeriesUseCase.execute(specialty, series)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                books -> {
                                    bookLiveData.setValue(books);
                                    },
                                throwable -> {
                                    eventLiveData.setValue(throwable.getMessage());
                                })
        );
    }

    public void export() {
        eventLiveData.setValue("Export");
    }
}
