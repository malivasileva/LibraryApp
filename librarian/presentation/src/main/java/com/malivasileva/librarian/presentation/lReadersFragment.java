package com.malivasileva.librarian.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.malivasileva.librarian.presentation.adapters.ReaderAdapter;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.FragmentLLendingsBinding;
import com.malivasileva.presentation.databinding.FragmentLReadersBinding;

import java.util.ArrayList;

public class lReadersFragment extends Fragment {

    private FragmentLReadersBinding binding;
    private LibrarianViewModel viewModel;
    private ReaderAdapter readerAdapter;

    public lReadersFragment() {
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
        binding = FragmentLReadersBinding.inflate(inflater, container, false);

        readerAdapter = new ReaderAdapter(new ArrayList<>());
        binding.readersRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.readersRv.setAdapter(readerAdapter);

        viewModel.getReadersLiveData().observe(getViewLifecycleOwner(), readers -> {
            Log.d("govno-observe", readers.toString());
            if (readers != null) {
                readerAdapter.updateReaders(readers);
                if (!readers.isEmpty()) {
                    binding.readersRv.setVisibility(View.VISIBLE);
                    binding.readerPlaceholder.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.searchReaders.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchReaders.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        viewModel.searchReader(query);
                        binding.readersRv.setVisibility(View.VISIBLE);
                        binding.readerPlaceholder.setVisibility(View.INVISIBLE);
                        binding.searchReaders.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}