package com.malivasileva.librarian.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.AddBookInSylabusUseCase;
import com.malivasileva.librarian.domain.usecases.DeleteBookFromSylabusUseCase;
import com.malivasileva.librarian.domain.usecases.GetBookWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.GetStudySeriesUseCase;
import com.malivasileva.librarian.domain.usecases.GetSubjectsForSpecialtyUseCase;
import com.malivasileva.librarian.domain.usecases.GetSylabusForSpecialtyUseCase;
import com.malivasileva.model.Book;
import com.malivasileva.model.StudySeries;
import com.malivasileva.model.Subject;
import com.malivasileva.model.Sylabus;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SylabusViewModel extends ViewModel {

    private final GetSylabusForSpecialtyUseCase getSylabusForSpecialtyUseCase;
    private final GetStudySeriesUseCase getStudySeriesUseCase;
    private final GetBookWithIdUseCase getBookWithIdUseCase;
    private final GetSubjectsForSpecialtyUseCase getSubjectsForSpecialtyUseCase;
    private final DeleteBookFromSylabusUseCase deleteBookFromSylabusUseCase;
    private final AddBookInSylabusUseCase addBookInSylabusUseCase;


    private MutableLiveData<List<Sylabus>> sylabusLiveData = new MutableLiveData<>();
    private MutableLiveData<List<StudySeries>> seriesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Subject>> subjectsLiveData = new MutableLiveData<>();
    private MutableLiveData<Book> bookLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SylabusViewModel(
            GetSylabusForSpecialtyUseCase getSylabusForSpecialtyUseCase,
            GetStudySeriesUseCase getStudySeriesUseCase,
            GetBookWithIdUseCase getBookWithIdUseCase,
            GetSubjectsForSpecialtyUseCase getSubjectsForSpecialtyUseCase,
            DeleteBookFromSylabusUseCase deleteBookFromSylabusUseCase,
            AddBookInSylabusUseCase addBookInSylabusUseCase
    ) {
        this.getSylabusForSpecialtyUseCase = getSylabusForSpecialtyUseCase;
        this.getStudySeriesUseCase = getStudySeriesUseCase;
        this.getBookWithIdUseCase = getBookWithIdUseCase;
        this.getSubjectsForSpecialtyUseCase = getSubjectsForSpecialtyUseCase;
        this.deleteBookFromSylabusUseCase = deleteBookFromSylabusUseCase;
        this.addBookInSylabusUseCase = addBookInSylabusUseCase;
    }

    public LiveData<List<Sylabus>> getSylabusLiveData() { return sylabusLiveData; }
    public LiveData<List<StudySeries>> getSeriesLiveData() { return seriesLiveData; }
    public LiveData<List<Subject>> getSubjectLiveData() { return subjectsLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }
    public LiveData<Book> getBookLiveData() { return bookLiveData; }

    public void getSylabus(int num) {
        disposable.add(
                getSylabusForSpecialtyUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                sylabuses -> {
                            sylabusLiveData.setValue(sylabuses);
                            },
                            throwable -> {
                                eventLiveData.setValue("Ошибка получения данных");
                            }
                            )
        );
    }

    public void getSeries() {
        disposable.add(
                getStudySeriesUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                series -> {
                                    seriesLiveData.setValue(series);
                                },
                                throwable -> {
                                    eventLiveData.setValue("Ошибка получения данных");
                                }
                        )
        );
    }

    public void getSubjects(int specialty) {
        disposable.add(
                getSubjectsForSpecialtyUseCase.execute(specialty)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subjects -> {
                            subjectsLiveData.setValue(subjects);
                        })
        );
    }

    public void getBookWithId(int id) {
        disposable.add(
                getBookWithIdUseCase.execute(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(book -> {
                            bookLiveData.setValue(book);
                        })
        );
    }

    public void addBookInSylabus(int studyPlan, int book) {
        disposable.add(
                addBookInSylabusUseCase.execute(studyPlan, book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            String msg;
                            if (result) {
                                msg = "Книга добавлена!";
                            } else {
                                msg = "Ошибка";
                            }
                            eventLiveData.setValue(msg);
                        })
        );
    }

    public void deleteBookFromSylabus(int studyPlan, int book) {
        disposable.add(
                deleteBookFromSylabusUseCase.execute(studyPlan, book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            String msg;
                            if (result) {
                                msg = "Книга удалена";
                            } else {
                                msg = "Ошибка";
                            }
                            eventLiveData.setValue(msg);
                        })
        );
    }
}
