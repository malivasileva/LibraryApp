package com.malivasileva.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.presentation.adapters.MainAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class rMainFragment extends Fragment {

    TextView testText;
    ReaderViewModel viewModel;
    RecyclerView recyclerView;
    MainAdapter adapter;

    public rMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ReaderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_main, container, false);

        recyclerView = view.findViewById(R.id.main_rv);
        adapter = new MainAdapter(new ArrayList<>());

        testText = view.findViewById(R.id.test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCurrentLendingsLiveData().observe(getViewLifecycleOwner(), lendings -> {
            if (lendings != null && !lendings.isEmpty()) {
                adapter.updateLendings(lendings);
            }
        });

        // Вызов метода получения данных
        viewModel.getCurrentLendings();
    }
}