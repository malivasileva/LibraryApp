package com.malivasileva.librarian.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malivasileva.librarian.presentation.adapters.LendingAdapter;
import com.malivasileva.librarian.presentation.adapters.ReaderAdapter;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.FragmentLBooksBinding;
import com.malivasileva.presentation.databinding.FragmentLLendingsBinding;

import java.util.ArrayList;


public class lLendingsFragment extends Fragment {
    private FragmentLLendingsBinding binding;
    private LibrarianViewModel viewModel;
    private LendingAdapter lendingAdapter;

    public lLendingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LibrarianViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLLendingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        lendingAdapter = new LendingAdapter(new ArrayList<>());
        binding.lendingsRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.lendingsRv.setAdapter(lendingAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Очищаем binding для предотвращения утечек памяти
    }
}