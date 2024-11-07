package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.ReaderAdapter;
import com.malivasileva.librarian.presentation.viewModels.LibrarianViewModel;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentWithActionBinding;

import java.util.ArrayList;

public class lReadersFragment extends Fragment {

    private SearchFragmentWithActionBinding binding;
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
        binding = SearchFragmentWithActionBinding.inflate(inflater, container, false);

        readerAdapter = new ReaderAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(readerAdapter);

        binding.searchView.setQueryHint(getString(R.string.search_readers));

        viewModel.getReadersLiveData().observe(getViewLifecycleOwner(), readers -> {
            Log.d("govno-observe", readers.toString());
            if (readers != null) {
                readerAdapter.updateReaders(readers);
                if (!readers.isEmpty()) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.textPlaceholder.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchReader(query);
                binding.textPlaceholder.setVisibility(View.INVISIBLE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.searchView.clearFocus(); // Снимает фокус с SearchView после отправки
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Здесь можно добавить логику для обновления результатов по мере ввода текста
                return false;
            }
        });

        binding.button.setText(getString(R.string.get_active_readers));
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getActiveReaders();
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