package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.malivasileva.librarian.presentation.adapters.SylabusAdapter;
import com.malivasileva.librarian.presentation.viewModels.SylabusViewModel;
import com.malivasileva.presentation.databinding.SearchFragmentWithActionBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FormForthFragment extends Fragment {
    private SearchFragmentWithActionBinding binding;

    private static final String ARG_SPEC_ID = "specialty_id";
    private static final String ARG_CYCLE_ID = "cycle_id";
    private int specialtyId;
    private int cycleId;
    private SylabusAdapter lendingAdapter;
    private SylabusViewModel viewModel;

    public static FormForthFragment newInstance(int specialtyId, int cycleId) {
        FormForthFragment fragment = new FormForthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SPEC_ID, specialtyId);
        args.putInt(ARG_CYCLE_ID, cycleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            specialtyId = getArguments().getInt(ARG_SPEC_ID);
            cycleId = getArguments().getInt(ARG_CYCLE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentWithActionBinding.inflate(inflater, container, false);

        binding.textPlaceholder.setText("Специальность: " + specialtyId + "/Цикл: " + cycleId);

        return binding.getRoot();
    }
}
