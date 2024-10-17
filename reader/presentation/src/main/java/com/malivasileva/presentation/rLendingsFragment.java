package com.malivasileva.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.presentation.adapters.LendingAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class rLendingsFragment extends Fragment {


    private RecyclerView recyclerView;
    private LendingAdapter lendingAdapter;
    ReaderViewModel viewModel;

    public rLendingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_r_lendings, container, false);

        recyclerView = view.findViewById(R.id.lendings_rv);
        lendingAdapter = new LendingAdapter(new ArrayList<>());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(lendingAdapter);

        viewModel.getLendingsLiveData().observe(getViewLifecycleOwner(), lendings -> {
            if (lendings != null) {
                lendingAdapter.updateLendings(lendings);
            }
        });

        return view;
    }
}