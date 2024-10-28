package com.malivasileva.reader.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.reader.presentation.adapters.BookAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class rBooksFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    private ReaderViewModel viewModel;


    public rBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReaderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_books, container, false);
        searchView = view.findViewById(R.id.search_book);
        recyclerView = view.findViewById(R.id.books_rv); // Убедитесь, что у вас есть этот ID в XML
        bookAdapter = new BookAdapter(new ArrayList<>());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);


        // Наблюдение за booksLiveData
        viewModel.getBooksLiveData().observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                bookAdapter.updateBooks(books); // Обновление данных в адаптере RecyclerView
            }
        });

        // Наблюдение за errorLiveData
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                // Показ ошибки пользователю (например, через Toast)
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
//                        loadBooks(query);
                        viewModel.searchBooks(query);
                        searchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
        });

        return view;
    }

    private void loadBooks(String query) {

    }
}