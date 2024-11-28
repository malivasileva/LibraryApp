package com.malivasileva.librarian.presentation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.malivasileva.librarian.presentation.adapters.SylabusAdapter;
import com.malivasileva.librarian.presentation.viewModels.SylabusViewModel;
import com.malivasileva.model.StudySeries;
import com.malivasileva.model.Subject;
import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.SearchFragmentWithActionBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SylabusFragment extends Fragment {
    private static final String ARG_SPEC_ID = "specialty_id";
    private static final String ARG_TITLE = "spec_title";
    private String title = "error";
    private int specialtyId;
    private int selectedSubject = -1;
    private int selectedBook = -1;
    private List<StudySeries> series;
    private List<Subject> subjects;
    private SearchFragmentWithActionBinding binding;
    private SylabusAdapter sylabusAdapter;
    private SylabusViewModel viewModel;

    public static SylabusFragment newInstance(int specialtyId, String title) {
        SylabusFragment fragment = new SylabusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SPEC_ID, specialtyId);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SylabusViewModel.class);
        if (getArguments() != null) {
            specialtyId = getArguments().getInt(ARG_SPEC_ID);
            title = getArguments().getString(ARG_TITLE);
            viewModel.getSylabus(specialtyId);
            viewModel.getSeries();
            viewModel.getSubjects(specialtyId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentWithActionBinding.inflate(inflater, container, false);

        binding.textPlaceholder.setText("Добавьте книги в список литературы");
        binding.button.setText("Добавить");
        binding.searchView.setVisibility(View.INVISIBLE);
        binding.hiddenButton.setVisibility(View.VISIBLE);

        sylabusAdapter = new SylabusAdapter(new ArrayList<>(), (int book, int studyPlan) -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Удалить элемент?")
                    .setCancelable(true)
                    .setPositiveButton("Да", (dialog, which) -> {
                        viewModel.deleteBookFromSylabus(studyPlan, book);
                        viewModel.getSylabus(specialtyId);
                    })
                    .setNegativeButton("Нет", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(sylabusAdapter);

        viewModel.getSylabusLiveData().observe(getViewLifecycleOwner(), sylabuses -> {
            if (sylabuses != null && !sylabuses.isEmpty()) {
                sylabusAdapter.updateList(sylabuses);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.textPlaceholder.setVisibility(View.INVISIBLE);
            } else {
                binding.recyclerView.setVisibility(View.INVISIBLE);
                binding.textPlaceholder.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getSeriesLiveData().observe(getViewLifecycleOwner(), seriesResult -> {
            series = seriesResult;
        });

        viewModel.getSubjectLiveData().observe(getViewLifecycleOwner(), subjectsResult -> {
            subjects = subjectsResult;
        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        });



        //Form Four creating
        binding.hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.choose_series, null);
                builder.setView(dialogView);

                ListView listView = dialogView.findViewById(R.id.listViewItems);
                ArrayAdapter<StudySeries> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        series
                );
                listView.setAdapter(adapter);


                AlertDialog dialog = builder.setTitle("Выберите цикл")
                        .setCancelable(true)
                        .create();
                dialog.show();

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    StudySeries selectedSeries = (StudySeries) parent.getItemAtPosition(position);
//                    int selectedSeriesId = selectedSeries.getNum();
                    FormForthFragment formForthFragment = FormForthFragment.newInstance(specialtyId, title, selectedSeries.getNum(), selectedSeries.getName());
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.l_frame, formForthFragment)
                            .addToBackStack(null)
                            .commit();

                    dialog.dismiss();
                });
            }
        });

        //Adding book in sylabus
        binding.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_sylabus, null);
                builder.setView(dialogView);


                Spinner spinner = dialogView.findViewById(R.id.subject_spinner);
                ImageButton bookBtn = dialogView.findViewById(R.id.get_book_btn);
                EditText book = dialogView.findViewById(R.id.book);
                TextView title = dialogView.findViewById(R.id.book_title);
                TextView authors = dialogView.findViewById(R.id.authors);

                ArrayAdapter<Subject> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, subjects);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Subject selectedSubject = (Subject) parent.getItemAtPosition(position);
                                SylabusFragment.this.selectedSubject = selectedSubject.getNum();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                selectedSubject = -1;
                            }
                        }
                );

                bookBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bookNum = book.getText().toString();
                        if (!bookNum.isEmpty()) {
                            viewModel.getBookWithId(Integer.parseInt(bookNum));
                            selectedBook = Integer.parseInt(bookNum);
                        }
                    }
                });

                viewModel.getBookLiveData().observe(getViewLifecycleOwner(), data -> {
                    title.setText(data.getTitle());
                    authors.setText(data.getAuthors());
                });

                AlertDialog dialog = builder.setTitle("Добавить книгу")
                        .setCancelable(true)
                        .setPositiveButton("Добавить", (dialog1, which) -> {
                            if (selectedSubject != -1 && selectedBook != -1){
                                viewModel.addBookInSylabus(selectedSubject, selectedBook);
                                viewModel.getSylabus(specialtyId);
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LibrarianActivity) requireActivity()).updateTopAppTitle(title);
    }
}
