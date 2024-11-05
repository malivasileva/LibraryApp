package com.malivasileva.librarian.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.ExitLibrarianUseCase;
import com.malivasileva.librarian.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.librarian.domain.usecases.GetAllSpecialtiesUseCase;
import com.malivasileva.librarian.domain.usecases.SearchBooksUseCase;
import com.malivasileva.librarian.domain.usecases.SearchReaderUseCase;
import com.malivasileva.librarian.domain.usecases.GetReadersWithActiveLendingsUseCase;
import com.malivasileva.librarian.domain.usecases.SearchSpecialtyUseCase;
import com.malivasileva.model.Book;
import com.malivasileva.model.Lending;
import com.malivasileva.model.Reader;
import com.malivasileva.model.Specialty;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LibrarianViewModel extends ViewModel {

    private final ExitLibrarianUseCase exitLibrarianUseCase;
    private final SearchBooksUseCase searchBooksUseCase;
    private final SearchReaderUseCase searchReaderUseCase;
    private final SearchSpecialtyUseCase searchSpecialtyUseCase;
    private final GetAllSpecialtiesUseCase getAllSpecialtiesUseCase;
    private final GetCurrentLendingsUseCase getCurrentLendingsUseCase;
    private final GetReadersWithActiveLendingsUseCase getReadersWithActiveLendingsUseCase;

    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Reader>> readersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Lending>> lendingsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Specialty>> specialtyLiveData = new MutableLiveData<>();

    private final CompositeDisposable disposables = new CompositeDisposable();

    public LiveData<List<Book>> getBooksLiveData() { return booksLiveData; }
    public LiveData<List<Reader>> getReadersLiveData() { return readersLiveData; }
    public LiveData<List<Lending>> getLendingsLiveData() { return lendingsLiveData; }
    public LiveData<List<Specialty>> getSpecialtyLiveData() { return specialtyLiveData; }

    @Inject
    public LibrarianViewModel(ExitLibrarianUseCase exitLibrarianUseCase,
                              SearchBooksUseCase searchBooksUseCase,
                              SearchReaderUseCase searchReaderUseCase,
                              SearchSpecialtyUseCase searchSpecialtyUseCase,
                              GetAllSpecialtiesUseCase getAllSpecialtiesUseCase,
                              GetCurrentLendingsUseCase getCurrentLendingsUseCase,
                              GetReadersWithActiveLendingsUseCase getReadersWithActiveLendingsUseCase) {
        this.exitLibrarianUseCase = exitLibrarianUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
        this.searchReaderUseCase = searchReaderUseCase;
        this.searchSpecialtyUseCase = searchSpecialtyUseCase;
        this.getAllSpecialtiesUseCase = getAllSpecialtiesUseCase;
        this.getCurrentLendingsUseCase = getCurrentLendingsUseCase;
        this.getReadersWithActiveLendingsUseCase = getReadersWithActiveLendingsUseCase;

        getAllSpecialties();
        getAllCurrentLendings();
    }

    public void exit() {
        disposables.add(
                exitLibrarianUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

    public void searchBooks(String query) {
        disposables.add(
            searchBooksUseCase.execute(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            books -> { booksLiveData.setValue(books); },
                            throwable -> { errorLiveData.setValue("Ошибка поиска книг: " + throwable.getMessage()); }
                    )
        );
    }

    public void searchReader(String query) {
        disposables.add(
                searchReaderUseCase.execute(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                readers -> {
                                    readersLiveData.setValue(readers);
                                    },
                                throwable -> { errorLiveData.setValue("Ошибка поиска читателей: " + throwable.getMessage()); }
                        )
        );
    }

    public void searchSpecialty(String query) {
        disposables.add(
                searchSpecialtyUseCase.execute(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                specialties -> { specialtyLiveData.setValue(specialties); },
                                throwable -> { errorLiveData.setValue("Ошибка поиска специальностей: " + throwable.getMessage()); }
                        )
        );
    }

    public void getAllSpecialties() {
        disposables.add(
                getAllSpecialtiesUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                specialties -> { specialtyLiveData.setValue(specialties); },
                                throwable -> { errorLiveData.setValue("Ошибка поиска специальностей: " + throwable.getMessage()); }
                        )
        );
    }

    public void getAllCurrentLendings() {
        disposables.add(
                getCurrentLendingsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                lendings -> { lendingsLiveData.setValue(lendings); },
                                throwable -> { errorLiveData.setValue("Ошибка поиска выдач: " + throwable.getMessage()); }
                        )
        );
    }

    public void getActiveReaders() {
        disposables.add(
                getReadersWithActiveLendingsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                readers -> {
                                    readersLiveData.setValue(readers);
                                },
                                throwable -> { errorLiveData.setValue("Ошибка поиска читателей: " + throwable.getMessage()); }
                        )
        );
    }

}
