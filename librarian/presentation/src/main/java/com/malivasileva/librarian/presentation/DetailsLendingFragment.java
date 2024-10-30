package com.malivasileva.librarian.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.presentation.databinding.DetailLendingBinding;
import com.malivasileva.resources.R;

import java.text.SimpleDateFormat;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class DetailsLendingFragment extends Fragment {

    private static final String ARG_LENDING_ID = "lending_id";
    private int lendingId;
    private DetailLendingBinding binding;
    private LendingViewModel viewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static DetailsLendingFragment newInstance(int lendingId) {
        DetailsLendingFragment fragment = new DetailsLendingFragment();
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
        }
        viewModel = new ViewModelProvider(requireActivity()).get(LendingViewModel.class);
        viewModel.getLending(lendingId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DetailLendingBinding.inflate(inflater, container, false);

        binding.pageTitle.setText("№ " + String.valueOf(lendingId));

        viewModel.getLendingLiveData().observe(getViewLifecycleOwner(), lending -> {

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
        });

        // Display button if book is not returned


        binding.editReaderNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && Integer.parseInt(binding.readerNum.getText().toString()) != viewModel.getLendingLiveData().getValue().getReaderId()) {
                    viewModel.updateLending(
                            Integer.parseInt(binding.readerNum.getText().toString()),
                            viewModel.getLendingLiveData().getValue().getBookId());
                }

                binding.readerNum.setEnabled(isChecked);
            }
        });

        binding.editBookNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && Integer.parseInt(binding.bookNum.getText().toString()) != viewModel.getLendingLiveData().getValue().getBookId()) {
                    viewModel.updateLending(
                            viewModel.getLendingLiveData().getValue().getReaderId(),
                            Integer.parseInt(binding.bookNum.getText().toString())
                            );
                }

                binding.bookNum.setEnabled(isChecked);
            }
        });

        binding.expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.expendReturnDate();
            }
        });

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.returnBook();
            }
        });

        return binding.getRoot();
    }
}
