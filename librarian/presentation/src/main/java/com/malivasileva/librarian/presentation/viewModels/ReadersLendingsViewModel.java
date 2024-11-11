package com.malivasileva.librarian.presentation.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.GetLendingsForReaderWithId;
import com.malivasileva.model.Lending;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ReadersLendingsViewModel extends ViewModel {
    private final GetLendingsForReaderWithId getLendingsForReaderWithId;

    private MutableLiveData<List<Lending>> lendingLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ReadersLendingsViewModel (
            GetLendingsForReaderWithId getLendingsForReaderWithId
    ) {
        this.getLendingsForReaderWithId = getLendingsForReaderWithId;
    }

    public LiveData<List<Lending>> getLendingLiveData() { return lendingLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getLendingForReader(int num) {
        disposable.add(
                getLendingsForReaderWithId.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( lendings -> {
                            lendingLiveData.setValue(lendings);
                            Log.d("viewModel", lendings.get(0).getTitle());
                        },
                                throwable -> {
                            eventLiveData.setValue(throwable.getMessage());
                                })
        );
    }
}
