package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.librarian.presentation.viewModels.AddLendingViewModel;
import com.malivasileva.presentation.databinding.DetailLendingBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddLendingFragment extends Fragment {

    private static final String ARG_LENDING_ID = "lending_id";
    private int lendingId;
    private DetailLendingBinding binding;
    private AddLendingViewModel viewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static AddLendingFragment newInstance() {
//        AddLendingFragment fragment = new AddLendingFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_LENDING_ID, lendingId);
//        fragment.setArguments(args);
        return new AddLendingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lendingId = getArguments().getInt(ARG_LENDING_ID);
        } else {
            //todo ???
        }

        viewModel = new ViewModelProvider(requireActivity()).get(AddLendingViewModel.class);
//        viewModel.getLending(lendingId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DetailLendingBinding.inflate(inflater, container, false);

        binding.pageTitle.setText("Новая выдача");
        binding.returnedDate.setVisibility(View.GONE);
        binding.textReturnedDate.setVisibility(View.GONE);

        Date today = new Date();
        binding.lendDate.setText(dateFormat.format(today));
        binding.requiredDate.setText(dateFormat.format(new Date(today.getTime() + 10 * 24 * 60 * 60 + 1000)));

        binding.returnButton.setVisibility(View.VISIBLE);
        binding.returnButton.setText("Выдать");

        binding.returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewModel.addLending(
                        Integer.parseInt(binding.readerNum.getText().toString()),
                        Integer.parseInt(binding.bookNum.getText().toString())
                );
            }
        });

        viewModel.getReaderLiveData().observe(getViewLifecycleOwner(), reader -> {
            if (reader != null) {
                binding.readerName.setText(reader.getName());
            }
        });

        viewModel.getBookLiveData().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                binding.bookTitle.setText(book.getTitle());
                binding.bookAuthors.setText(book.getAuthors());
            }
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.editReaderNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int readerNumEditText;
                String readerNumText = binding.readerNum.getText().toString().trim();

                if (readerNumText.isEmpty()) {
                    // Задайте значение по умолчанию или обработайте ошибку
                    readerNumEditText = 0; // Например, значение по умолчанию
                } else {
                    readerNumEditText = Integer.parseInt(readerNumText);
                }

                if (isChecked) {
                    binding.readerNum.findFocus();
                } else if (viewModel.getReaderLiveData().getValue() == null || readerNumEditText != viewModel.getReaderLiveData().getValue().getCard()) {
                    viewModel.getReader(
                            readerNumEditText
                    );
                }
                binding.readerNum.setEnabled(isChecked);
            }
        });

        binding.editBookNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int bookNumEditText;
                String bookNumText = binding.bookNum.getText().toString().trim();

                if (bookNumText.isEmpty()) {
                    // Установите значение по умолчанию или обработайте ошибку
                    bookNumEditText = 0; // Например, значение по умолчанию
                } else {
                    bookNumEditText = Integer.parseInt(bookNumText);
                }

                if (isChecked) {
                    binding.bookNum.findFocus();
                } else if (viewModel.getBookLiveData().getValue() == null || bookNumEditText != viewModel.getBookLiveData().getValue().getId()) {
                    viewModel.getBook(
                            bookNumEditText
                            );
                }

                binding.bookNum.setEnabled(isChecked);
            }
        });

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addLending(
                        Integer.parseInt(binding.readerNum.getText().toString()),
                        Integer.parseInt(binding.bookNum.getText().toString())
                );
            }
        });

        return binding.getRoot();
    }
}
