package com.malivasileva.presentation;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.domain.model.Book;
import com.malivasileva.domain.model.Reader;
import com.malivasileva.domain.usecases.DeleteProfileUseCase;
import com.malivasileva.domain.usecases.GetAllLendingsUseCase;
import com.malivasileva.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.domain.usecases.GetProfileUseCase;
import com.malivasileva.domain.usecases.SaveProfileUseCase;
import com.malivasileva.domain.usecases.SearchBookUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ReaderViewModel extends ViewModel {

    private final GetAllLendingsUseCase getAllLendingsUseCase;
    private final GetCurrentLendingsUseCase getCurrentLendingsUseCase;
    private final SearchBookUseCase searchBookUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final SaveProfileUseCase saveProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;

    private final MutableLiveData<Reader> profileLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();  // Для управления подписками

    @Inject
    public ReaderViewModel(GetAllLendingsUseCase getAllLendingsUseCase, GetCurrentLendingsUseCase getCurrentLendingsUseCase, SearchBookUseCase searchBookUseCase, GetProfileUseCase getProfileUseCase, SaveProfileUseCase saveProfileUseCase, DeleteProfileUseCase deleteProfileUseCase) {
        this.getAllLendingsUseCase = getAllLendingsUseCase;
        this.getCurrentLendingsUseCase = getCurrentLendingsUseCase;
        this.searchBookUseCase = searchBookUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.saveProfileUseCase = saveProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;

        Log.d("govno-viewmodel", "in constructor");
        getProfile();
    }

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

    public LiveData<Reader> getProfileLiveData() {
        return profileLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void searchBooks(String query) {
//        Log.d("govno-viewmodel", "in searchBooks");
        disposables.add(
                searchBookUseCase.execute(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                books -> {
//                                    Log.d("govno-viewmodel", "in subscribe");
                                    booksLiveData.setValue(books);
                                },
                                throwable -> {
//                                    Log.d("govno-viewmodel", "in error");
                                    errorLiveData.setValue("Error fetching books: " + throwable.getMessage());
                                }
                        )
        );
//        Log.d("govno-viewmodel", "finished searchBooks");
    }

    public void getProfile() {
        Log.d("govno-viewmodel", "in getProfile");
        disposables.add(
            getProfileUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            reader -> {
                                Log.d("govno-viewmodel", "in subscribe");
                                profileLiveData.setValue(reader);
                            },
                            throwable -> {
                                throwable.printStackTrace();
                            }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
