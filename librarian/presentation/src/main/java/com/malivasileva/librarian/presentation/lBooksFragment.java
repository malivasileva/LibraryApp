package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.BookAdapter;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentBinding;

import java.util.ArrayList;


public class lBooksFragment extends Fragment {

    private SearchFragmentBinding binding;
    private LibrarianViewModel viewModel;

    private BookAdapter bookAdapter;

    public lBooksFragment() {
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
        binding = SearchFragmentBinding.inflate(inflater, container, false);

        bookAdapter = new BookAdapter(new ArrayList<>());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(bookAdapter);

        binding.searchView.setQueryHint(getString(R.string.search_books));
//        binding.recyclerView.setVisibility(View.INVISIBLE);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchBooks(query);
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

        viewModel.getBooksLiveData().observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                bookAdapter.updateBooks(books);
                if (!books.isEmpty()) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.textPlaceholder.setVisibility(View.INVISIBLE);
                }
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
        binding = null; // Очищаем binding для предотвращения утечек памяти
    }
}