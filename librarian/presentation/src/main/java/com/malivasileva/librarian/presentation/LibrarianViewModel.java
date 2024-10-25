package com.malivasileva.librarian.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.ExitLibrarianUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LibrarianViewModel extends ViewModel {

    private final ExitLibrarianUseCase exitLibrarianUseCase;

    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public LibrarianViewModel(ExitLibrarianUseCase exitLibrarianUseCase) {
        this.exitLibrarianUseCase = exitLibrarianUseCase;
    }

    public void exit() {
        disposables.add(
                exitLibrarianUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

}
