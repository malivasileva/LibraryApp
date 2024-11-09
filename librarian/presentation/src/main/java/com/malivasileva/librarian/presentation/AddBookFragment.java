package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.librarian.presentation.viewModels.AddBookViewModel;
import com.malivasileva.model.Book;
import com.malivasileva.presentation.databinding.NewBookBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddBookFragment extends Fragment {

    private NewBookBinding binding;
    private AddBookViewModel viewModel;

    public static AddBookFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NewBookBinding.inflate(inflater, container, false);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book newBook = new Book(
                        binding.title.getText().toString().trim(),
                        binding.authors.getText().toString().trim(),
                        binding.address.getText().toString().trim(),
                        binding.publisher.getText().toString().trim(),
                        Integer.parseInt(binding.page.getText().toString().trim()),
                        Float.parseFloat(binding.price.getText().toString().trim()),
                        Integer.parseInt(binding.copy.getText().toString().trim()),
                        Integer.parseInt(binding.year.getText().toString().trim())
                );

                viewModel.addBook(newBook, () -> {
                    clearForm();
                });
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAllFieldsFilled();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Добавить текстовые слушатели к каждому EditText
        binding.title.addTextChangedListener(textWatcher);
        binding.authors.addTextChangedListener(textWatcher);
        binding.address.addTextChangedListener(textWatcher);
        binding.publisher.addTextChangedListener(textWatcher);
        binding.page.addTextChangedListener(textWatcher);
        binding.price.addTextChangedListener(textWatcher);
        binding.copy.addTextChangedListener(textWatcher);
        binding.year.addTextChangedListener(textWatcher);


        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }


    private void checkAllFieldsFilled() {
        boolean allFieldsFilled = !binding.title.getText().toString().trim().isEmpty() &&
                !binding.authors.getText().toString().trim().isEmpty() &&
                !binding.address.getText().toString().trim().isEmpty() &&
                !binding.publisher.getText().toString().trim().isEmpty() &&
                !binding.page.getText().toString().trim().isEmpty() &&
                !binding.price.getText().toString().trim().isEmpty() &&
                !binding.copy.getText().toString().trim().isEmpty() &&
                !binding.year.getText().toString().trim().isEmpty();

        binding.saveButton.setEnabled(allFieldsFilled);
    }

    private void clearForm() {
        binding.title.setText("");
        binding.authors.setText("");
        binding.address.setText("");
        binding.publisher.setText("");
        binding.price.setText("");
        binding.page.setText("");
        binding.copy.setText("");
        binding.year.setText("");
    }
}
