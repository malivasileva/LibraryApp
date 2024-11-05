package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.presentation.databinding.DetailLendingBinding;
import com.malivasileva.resources.R;

import java.text.SimpleDateFormat;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddLendingFragment extends Fragment {

    private static final String ARG_LENDING_ID = "lending_id";
    private int lendingId;
    private DetailLendingBinding binding;
    private AddLendingViewModel viewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static AddLendingFragment newInstance(int lendingId) {
        AddLendingFragment fragment = new AddLendingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LENDING_ID, lendingId);
        fragment.setArguments(args);
        return fragment;
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
        });

        /*viewModel.getLendingLiveData().observe(getViewLifecycleOwner(), lending -> {

            if (viewModel.getLendingLiveData().getValue().getReturnDate() == null) {
                binding.returnButton.setVisibility(View.VISIBLE);
            } else {
                binding.returnButton.setVisibility(View.INVISIBLE);
            }

            if (viewModel.canExtendLending()) {
                binding.expandButton.setVisibility(View.VISIBLE);
            } else {
                binding.expandButton.setVisibility(View.INVISIBLE);
            }

            binding.readerNum.setText(String.valueOf(lending.getReaderId()));
            binding.readerName.setText(lending.getReaderName());
            binding.bookNum.setText(String.valueOf(lending.getBookId()));
            binding.bookTitle.setText(lending.getTitle());
            binding.bookAuthors.setText(lending.getAuthors());
            binding.lendDate.setText(dateFormat.format(lending.getLendDate()));

            binding.requiredDate.setText(dateFormat.format(lending.getRequiredDate()));
            if (lending.getReturnDate() != null) {
                binding.returnedDate.setText(dateFormat.format(lending.getReturnDate()));
                binding.returnedDate.setTextColor(ContextCompat.getColor(requireActivity(), R.color.on_background));
            } else {
                binding.returnedDate.setText("не возвращено");
                if (viewModel.isExpired()) binding.returnedDate.setTextColor(ContextCompat.getColor(requireActivity(), R.color.secondary));
            }
        });*/


        binding.editReaderNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int readerNumEditText = Integer.parseInt(binding.readerNum.getText().toString());
                if (isChecked) {
                    binding.bookNum.findFocus();
                } else if (readerNumEditText != viewModel.getReaderLiveData().getValue().getCard()) {
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
                int bookNumEditText = Integer.parseInt(binding.bookNum.getText().toString());
                if (isChecked) {
                    binding.bookNum.findFocus();
                } else if (bookNumEditText != viewModel.getBookLiveData().getValue().getId()) {
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
