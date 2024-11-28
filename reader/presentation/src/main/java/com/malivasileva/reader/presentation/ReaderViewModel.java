package com.malivasileva.reader.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.model.Book;
import com.malivasileva.model.Lending;
import com.malivasileva.model.Reader;
import com.malivasileva.reader.domain.usecases.CheckIfBookAvailableUseCase;
import com.malivasileva.reader.domain.usecases.DeleteProfileUseCase;
import com.malivasileva.reader.domain.usecases.ExitUseCase;
import com.malivasileva.reader.domain.usecases.GetAllLendingsUseCase;
import com.malivasileva.reader.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.reader.domain.usecases.GetProfileUseCase;
import com.malivasileva.reader.domain.usecases.SaveProfileUseCase;
import com.malivasileva.reader.domain.usecases.SearchBookUseCase;

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
    private final ExitUseCase exitUseCase;
    private final CheckIfBookAvailableUseCase checkIfBookAvailableUseCase;

    private final MutableLiveData<Reader> profileLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> eventLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Lending>> lendingsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Lending>> currentLendingsLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();  // Для управления подписками

    @Inject
    public ReaderViewModel(
            GetAllLendingsUseCase getAllLendingsUseCase,
            GetCurrentLendingsUseCase getCurrentLendingsUseCase,
            SearchBookUseCase searchBookUseCase,
            GetProfileUseCase getProfileUseCase,
            SaveProfileUseCase saveProfileUseCase,
            DeleteProfileUseCase deleteProfileUseCase,
            ExitUseCase exitUseCase,
            CheckIfBookAvailableUseCase checkIfBookAvailableUseCase) {
        this.getAllLendingsUseCase = getAllLendingsUseCase;
        this.getCurrentLendingsUseCase = getCurrentLendingsUseCase;
        this.searchBookUseCase = searchBookUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.saveProfileUseCase = saveProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;
        this.exitUseCase = exitUseCase;
        this.checkIfBookAvailableUseCase = checkIfBookAvailableUseCase;

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

    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public LiveData<List<Lending>> getLendingsLiveData() { return lendingsLiveData; }

    public LiveData<List<Lending>> getCurrentLendingsLiveData() { return currentLendingsLiveData; }

    public void searchBooks(String query) {
        disposables.add(
                searchBookUseCase.execute(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                books -> {
                                    booksLiveData.setValue(books);
                                },
                                throwable -> {
                                    errorLiveData.setValue("Error fetching books: " + throwable.getMessage());
                                }
                        )
        );
    }

    public void getProfile() {
        disposables.add(
            getProfileUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            reader -> {
                                profileLiveData.setValue(reader);
                            },
                            throwable -> {
                                throwable.printStackTrace();
                                errorLiveData.setValue("Ошибка получения данных профиля.");
                            }
                        )
        );
    }

    public void updateProfile(String name, String phone, String address) {
        Reader reader = getProfileLiveData().getValue();
        reader.setName(name);
        reader.setPhone(phone);
        reader.setAddress(address);
        disposables.add(
                saveProfileUseCase.execute(reader)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    String message = result ? "Профиль успешно обновлен!" : "Произошла ошибка при обновлении профиля.";
                                    eventLiveData.setValue(message);
                                },
                                throwable -> {
                                    errorLiveData.setValue("Ошибка обновления профиля: " + throwable.getMessage());
                                }
                        )
        );
    }

    public void getLendings() {
        disposables.add(
                getAllLendingsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                lendings -> {
                                    lendingsLiveData.setValue(lendings);
                                },
                                throwable -> {
                                    errorLiveData.setValue("Ошибка получения данных");
                                }

                        )
        );
    }

    public void getCurrentLendings() {
        disposables.add(
                getCurrentLendingsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                lendings -> {
                                    currentLendingsLiveData.setValue(lendings);
                                },
                                throwable -> {
                                    errorLiveData.setValue("Ошибка получения данных");
                                }
                        )
        );
    }

    public void checkIfBookAvailable(int bookNum) {
        disposables.add(
                checkIfBookAvailableUseCase.execute(bookNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    if (result) {
                                        eventLiveData.setValue("Книга доступна");
                                    } else {
                                        errorLiveData.setValue("Нет свободных экземпляров");
                                    }
                                },
                                throwable -> {
                                    errorLiveData.setValue("Ошибка");
                                }
                        )
        );
    }

    public void exit() {
        disposables.add(
                exitUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

    public void deleteProfile() {
        disposables.add(
                deleteProfileUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    if (result) {
                                        eventLiveData.setValue("Профиль успешно удален!");
                                    } else {
                                        errorLiveData.setValue("Произошла ошибка при удалении профиля.");
                                    }
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
