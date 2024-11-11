package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.GetLendingsForBookWithIdUseCase;
import com.malivasileva.model.Lending;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class BookLendingsViewModel extends ViewModel {

    private final GetLendingsForBookWithIdUseCase getLendingsForBookWithIdUseCase;

    private MutableLiveData<List<Lending>> lendingsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public BookLendingsViewModel(
            GetLendingsForBookWithIdUseCase getLendingsForBookWithIdUseCase
    ) {
        this.getLendingsForBookWithIdUseCase = getLendingsForBookWithIdUseCase;
    }

    public LiveData<List<Lending>> getLendingLiveData() { return lendingsLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getBook(int num) {
        disposable.add(
                getLendingsForBookWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( lendings -> {
                            lendingsLiveData.setValue(lendings);
                                },
                                throwable -> {
                                    eventLiveData.setValue(throwable.getMessage());
                                }
                        )
        );
    }

    public void exportToPdf(){
        //todo
    }
}
