package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.FullBookAdapter;
import com.malivasileva.librarian.presentation.viewModels.FormFourViewModel;
import com.malivasileva.librarian.presentation.viewModels.SylabusViewModel;
import com.malivasileva.presentation.databinding.SearchFragmentWithActionBinding;

import java.text.Normalizer;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FormForthFragment extends Fragment {
    private SearchFragmentWithActionBinding binding;

    private static final String ARG_SPEC_ID = "specialty_id";
    private static final String ARG_SPEC_TITLE = "specialty_title";
    private static final String ARG_CYCLE_ID = "cycle_id";
    private static final String ARG_TITLE = "title";
    private int specialtyId;
    private int cycleId;
    private String specialtyTitle;
    private String title = "error";
    private FullBookAdapter fullBookAdapter;
    private FormFourViewModel viewModel;

    public static FormForthFragment newInstance(
            int specialtyId,
            String specialtyTitle,
            int cycleId,
            String pageTitle) {
        FormForthFragment fragment = new FormForthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SPEC_ID, specialtyId);
        args.putInt(ARG_CYCLE_ID, cycleId);
        args.putString(ARG_TITLE, pageTitle);
        args.putString(ARG_SPEC_TITLE, specialtyTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(FormFourViewModel.class);

        if (getArguments() != null) {
            specialtyId = getArguments().getInt(ARG_SPEC_ID);
            cycleId = getArguments().getInt(ARG_CYCLE_ID);
            title = getArguments().getString(ARG_TITLE);
            specialtyTitle = getArguments().getString(ARG_SPEC_TITLE);

            viewModel.getBooks(specialtyId, cycleId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentWithActionBinding.inflate(inflater, container, false);

        binding.searchView.setVisibility(View.GONE);
        binding.button.setText("Экспорт в PDF");

        fullBookAdapter = new FullBookAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(fullBookAdapter);
        binding.recyclerView.setVisibility(View.VISIBLE);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.export(requireActivity(),
                        Integer.toString(specialtyId),
                        specialtyTitle,
                        title);
            }
        });

        viewModel.getBookLiveData().observe(getViewLifecycleOwner(), bookList -> {
            fullBookAdapter.updateList(bookList);
        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LibrarianActivity) requireActivity()).updateTopAppTitle(title);
    }
}
