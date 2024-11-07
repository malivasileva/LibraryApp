package com.malivasileva.librarian.presentation.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.ExpandReturnDateUseCase;
import com.malivasileva.librarian.domain.usecases.GetLendingWithIdUseCase;
import com.malivasileva.librarian.domain.usecases.ReturnBookUseCase;
import com.malivasileva.librarian.domain.usecases.UpdateLendingUseCase;
import com.malivasileva.model.Lending;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LendingViewModel extends ViewModel {

    private final GetLendingWithIdUseCase getLendingWithIdUseCase;
    private final UpdateLendingUseCase updateLendingUseCase;
    private final ReturnBookUseCase returnBookUseCase;
    private final ExpandReturnDateUseCase expandReturnDateUseCase;

    private MutableLiveData<Lending> lendingLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public LendingViewModel (
            GetLendingWithIdUseCase getGetLendingWithIdUseCase,
            UpdateLendingUseCase updateLendingUseCase,
            ReturnBookUseCase returnBookUseCase,
            ExpandReturnDateUseCase expandReturnDateUseCase
    ) {
        this.getLendingWithIdUseCase = getGetLendingWithIdUseCase;
        this.updateLendingUseCase = updateLendingUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.expandReturnDateUseCase = expandReturnDateUseCase;
    }

    public LiveData<Lending> getLendingLiveData() {return lendingLiveData; }

    public void getLending(int num) {
        disposable.add(
                getLendingWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                lending -> {
                                    lendingLiveData.setValue(lending);
                                }
                                )
        );
    }

    public void updateLending(int reader, int book) {
        Lending currentLending = lendingLiveData.getValue();
        disposable.add(
                updateLendingUseCase.execute(
                        new Lending(
                                currentLending.getId(),
                                reader,
                                currentLending.getReaderName(),
                                book,
                                currentLending.getTitle(),
                                currentLending.getAuthors(),
                                currentLending.getLendDate(),
                                currentLending.getRequiredDate(),
                                currentLending.getReturnDate()
                        ))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    result -> {
                                        if (result) {
                                            getLending(currentLending.getId());
                                        }
                                    }
                            )
                );
    }

    public void returnBook() {
        disposable.add(
                returnBookUseCase.execute(lendingLiveData.getValue().getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( result -> {
                            Log.d("govno-view-model", result.toString());
                            if (result) {
                                getLending(lendingLiveData.getValue().getId());
                            }
                        })
        );
    }

    public void expendReturnDate() {
        disposable.add(
                expandReturnDateUseCase.execute(lendingLiveData.getValue().getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            if (result) {
                                getLending(lendingLiveData.getValue().getId());
                            }
                        })
        );
    }

    public boolean canExtendLending() {
        Date currentDate = new Date();
        Date lendDate = lendingLiveData.getValue().getLendDate();
        Date requiredDate = lendingLiveData.getValue().getRequiredDate();

        if (currentDate.before(requiredDate)) {
            long diffInMillies = Math.abs(lendDate.getTime() - (requiredDate.getTime() + TimeUnit.DAYS.toMillis(10)));
            long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diffDays <= 20;
        }
        return false;
    }

    public boolean isExpired() {
        Date currentDate = new Date();
        Date requiredDate = lendingLiveData.getValue().getRequiredDate();

        return currentDate.after(requiredDate);
    }

}
