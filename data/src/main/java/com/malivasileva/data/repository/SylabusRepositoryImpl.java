package com.malivasileva.data.repository;

import android.util.Log;

import com.malivasileva.data.DatabaseService;
import com.malivasileva.data.entities.SylabusEntity;
import com.malivasileva.librarian.domain.repositories.SylabusRepository;
import com.malivasileva.model.Sylabus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class SylabusRepositoryImpl implements SylabusRepository {

    DatabaseService databaseService;
    String TAG = "SylabusRepositoryImpl";

    public SylabusRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Single<List<Sylabus>> getSylabusFor(int specialtyNum) {
        return databaseService.getSylabusFor(specialtyNum)
                .map( entities -> entities.stream()
                        .map(this::mapEntityToModel)
                        .collect(Collectors.toList())
                )
                .onErrorReturn( throwable -> {
                    return Collections.emptyList();
                });
    }

    @Override
    public Single<Boolean> addBookInSylabus(int studyPlan, int book) {
        return databaseService.addBookInSylabus(studyPlan, book)
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    @Override
    public Single<Boolean> deleteBookFromSylabus(int studyPlan, int book) {
        return databaseService.deleteBookFromSylabus(studyPlan, book)
                .onErrorReturn(
                        throwable -> {
                            Log.e(TAG, "Произошла ошибка: " + throwable.getMessage(), throwable);
                            return false;
                        }
                );
    }

    public Sylabus mapEntityToModel(SylabusEntity entity) {
        return new Sylabus(
                entity.getSubject(),
                entity.getBookNum(),
                entity.getTitle(),
                entity.getAuthors(),
                entity.getStudyPlanNum()
        );
    }
}
