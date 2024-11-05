package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LibrLendingRepository {
    Single<List<Lending>> getLendingsForBookWith(int num);
    Single<List<Lending>> getCurrentLendings();
    Single<Lending> getLendingWithId(int lendingId);
    Single<Boolean> updateLending(Lending lending);
    Single<Boolean> returnBook(int lendingId);
    Single<Boolean> expandReturnDate(int lendingId);
    Single<Boolean> addLending(int cardNum, int bookNum);
}
