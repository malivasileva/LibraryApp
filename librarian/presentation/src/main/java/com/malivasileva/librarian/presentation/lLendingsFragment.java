package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.LendingAdapter;
import com.malivasileva.librarian.presentation.viewModels.LibrarianViewModel;
import com.malivasileva.model.Lending;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentBinding;

import java.util.ArrayList;


public class lLendingsFragment extends Fragment {
    private SearchFragmentBinding binding;
//    private LMainBinding rootBinding;
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
        binding = SearchFragmentBinding.inflate(inflater, container, false);
//        rootBinding = LMainBinding.inflate(inflater, container, false);

        lendingAdapter = new LendingAdapter(new ArrayList<Lending>(), lending -> {
            DetailsLendingFragment detailFragment = DetailsLendingFragment.newInstance(lending.getId());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.l_frame, detailFragment)  // R.id.frame_layout — ID вашего FrameLayout
                    .addToBackStack(null)  // Добавляет транзакцию в back stack
                    .commit();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(lendingAdapter);

        binding.searchView.setQueryHint(getString(R.string.search_lendings));
        binding.searchView.setVisibility(View.INVISIBLE);
        binding.textPlaceholder.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);

        viewModel.getLendingsLiveData().observe(getViewLifecycleOwner(), lendings -> {
            if (lendings != null) {
                lendingAdapter.updateList(lendings);
            }
        });

        /*binding.searchLendings.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchLendings.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
//                        viewModel.se(query);

                        binding.searchLendings.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
        });*/


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