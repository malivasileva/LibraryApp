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

import com.malivasileva.librarian.presentation.adapters.SpecialtyAdapter;
import com.malivasileva.librarian.presentation.viewModels.LibrarianViewModel;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentBinding;

import java.util.ArrayList;


public class lSpecialtyFragment extends Fragment {

    private SearchFragmentBinding binding;
    private LibrarianViewModel viewModel;
    private SpecialtyAdapter adapter;

    public lSpecialtyFragment() {
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

        adapter = new SpecialtyAdapter(new ArrayList<>(), specialty -> {
            SylabusFragment sylabus = SylabusFragment.newInstance(specialty.getNum(), specialty.getName());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.l_frame, sylabus)  // R.id.frame_layout — ID вашего FrameLayout
                    .addToBackStack(null)  // Добавляет транзакцию в back stack
                    .commit();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.searchView.setQueryHint(getString(R.string.search_specialty));
        binding.textPlaceholder.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);

        viewModel.getSpecialtyLiveData().observe(getViewLifecycleOwner(), specialties -> {
            if (specialties != null) {
                adapter.updateList(specialties);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchSpecialty(query);
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