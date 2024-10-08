package com.malivasileva.presentation;

import androidx.lifecycle.ViewModel;

import com.malivasileva.domain.usecases.DeleteProfileUseCase;
import com.malivasileva.domain.usecases.GetAllLendingsUseCase;
import com.malivasileva.domain.usecases.GetCurrentLendingsUseCase;
import com.malivasileva.domain.usecases.GetProfileUseCase;
import com.malivasileva.domain.usecases.SaveProfileUseCase;
import com.malivasileva.domain.usecases.SearchBookUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReaderViewModel extends ViewModel {

    private final GetAllLendingsUseCase getAllLendingsUseCase;
    private final GetCurrentLendingsUseCase getCurrentLendingsUseCase;
    private final SearchBookUseCase searchBookUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final SaveProfileUseCase saveProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;

    @Inject
    public ReaderViewModel(GetAllLendingsUseCase getAllLendingsUseCase, GetCurrentLendingsUseCase getCurrentLendingsUseCase, SearchBookUseCase searchBookUseCase, GetProfileUseCase getProfileUseCase, SaveProfileUseCase saveProfileUseCase, DeleteProfileUseCase deleteProfileUseCase) {
        this.getAllLendingsUseCase = getAllLendingsUseCase;
        this.getCurrentLendingsUseCase = getCurrentLendingsUseCase;
        this.searchBookUseCase = searchBookUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.saveProfileUseCase = saveProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;
    }


}
