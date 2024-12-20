package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.LendingAdapter;
import com.malivasileva.librarian.presentation.viewModels.ReadersLendingsViewModel;
import com.malivasileva.model.Lending;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReadersLendingsFragment extends Fragment {

    private static final String ARG_READER_ID = "reader_id";
    private int readerId;
    private SearchFragmentBinding binding;
    private LendingAdapter lendingAdapter;
    private ReadersLendingsViewModel viewModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static ReadersLendingsFragment newInstance(int readerId) {
        ReadersLendingsFragment fragment = new ReadersLendingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_READER_ID, readerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            readerId = getArguments().getInt(ARG_READER_ID);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(ReadersLendingsViewModel.class);

        viewModel.getLendingForReader(readerId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = SearchFragmentBinding.inflate(inflater, container, false);

        binding.searchView.setQueryHint("Искать выдачу");

        lendingAdapter = new LendingAdapter(new ArrayList<Lending>(), lending -> {
            DetailsLendingFragment detailFragment = DetailsLendingFragment.newInstance(lending.getId());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.l_frame, detailFragment)  // R.id.frame_layout — ID вашего FrameLayout
                    .addToBackStack(null)  // Добавляет транзакцию в back stack
                    .commit();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(lendingAdapter);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.textPlaceholder.setVisibility(View.INVISIBLE);

        viewModel.getLendingLiveData().observe(getViewLifecycleOwner(), lendings -> {
            if (lendings != null) {
                lendingAdapter.updateList(lendings);
            }
        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lendingAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return binding.getRoot();
    }
}
