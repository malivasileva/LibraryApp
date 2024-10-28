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
import android.widget.SearchView;

import com.malivasileva.librarian.presentation.adapters.BookAdapter;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.FragmentLBooksBinding;

import java.util.ArrayList;


public class lBooksFragment extends Fragment {

    private FragmentLBooksBinding binding;
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
        binding = FragmentLBooksBinding.inflate(inflater, container, false);

        bookAdapter = new BookAdapter(new ArrayList<>());

        binding.booksRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.booksRv.setAdapter(bookAdapter);

        binding.searchBook.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        viewModel.searchBooks(query);
                        binding.booksPlaceholder.setVisibility(View.INVISIBLE);
                        binding.booksRv.setVisibility(View.VISIBLE);
                        binding.searchBook.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
        });

        viewModel.getBooksLiveData().observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                bookAdapter.updateBooks(books);
                if (!books.isEmpty()) {
                    binding.booksRv.setVisibility(View.VISIBLE);
                    binding.booksPlaceholder.setVisibility(View.INVISIBLE);
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