package com.malivasileva.librarian.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.librarian.presentation.viewModels.BookViewModel;
import com.malivasileva.model.Book;
import com.malivasileva.presentation.databinding.DetailBookBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsBookFragment extends Fragment {

    BookViewModel viewModel;
    private DetailBookBinding binding;
    private static final String ARG_BOOK_ID = "book_id";
    private int bookId;

    public static DetailsBookFragment newInstance(int bookId) {
        DetailsBookFragment fragment = new DetailsBookFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        if (getArguments() != null) {
            bookId = getArguments().getInt(ARG_BOOK_ID);
            viewModel.getBook(bookId);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailBookBinding.inflate(inflater, container, false);

        viewModel.getBookLiveData().observe(getViewLifecycleOwner(), book -> {

            binding.pageTitle.setText("№ " + String.valueOf(book.getId()));
            binding.title.setText(book.getTitle());
            binding.authors.setText(book.getAuthors());
            binding.address.setText(book.getPublisheAddress());
            binding.publisher.setText(book.getPublisherName());
            binding.page.setText(String.valueOf(book.getPages()));
            binding.price.setText(String.valueOf(book.getPrice()));
            binding.copy.setText(String.valueOf(book.getCopies()));
            binding.year.setText(String.valueOf(book.getYear()));

        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
        });

        binding.editTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.title.setEnabled(isChecked);
            }
        });

        binding.editAuthors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.authors.setEnabled(isChecked);
            }
        });

        binding.editAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.address.setEnabled(isChecked);
            }
        });

        binding.editPublisher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.publisher.setEnabled(isChecked);
            }
        });

        binding.editPage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.page.setEnabled(isChecked);
            }
        });

        binding.editPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.price.setEnabled(isChecked);
            }
        });


        binding.editCopy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.copy.setEnabled(isChecked);
            }
        });

        binding.editYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.year.setEnabled(isChecked);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveBook();

                binding.editTitle.setChecked(false);
                binding.editAuthors.setChecked(false);
                binding.editAddress.setChecked(false);
                binding.editPublisher.setChecked(false);
                binding.editPage.setChecked(false);
                binding.editPrice.setChecked(false);
                binding.editCopy.setChecked(false);
                binding.editYear.setChecked(false);

            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Удалить книгу?")
                        .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.deleteBook(bookId);
                                dialog.dismiss();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            }
                        })
                        .setNegativeButton( "Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });

        binding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTextWatchers();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (checkIfBookDataChanged()) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Сохранить изменения?")
                    .setMessage("Вы хотите сохранить изменения перед выходом?")
                    .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveBook();
                        }
                    })
                    .setNegativeButton("Не сохранять", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();
        }

    }

    private boolean checkIfBookDataChanged() {
        Book currentBook = viewModel.getBookLiveData().getValue();

        boolean isTitleChanged = !binding.title.getText().toString().equals(currentBook.getTitle());
        boolean isAuthorsChanged = !binding.authors.getText().toString().equals(currentBook.getAuthors());
        boolean isAddressChanged = !binding.address.getText().toString().equals(currentBook.getPublisheAddress());
        boolean isPublisherChanged = !binding.publisher.getText().toString().equals(currentBook.getPublisherName());
        boolean isPageChanged = !binding.page.getText().toString().equals(String.valueOf(currentBook.getPages()));
        boolean isPriceChanged = !binding.price.getText().toString().equals(String.valueOf(currentBook.getPrice()));
        boolean isCopyChanged = !binding.copy.getText().toString().equals(String.valueOf(currentBook.getCopies()));
        boolean isYearChanged = !binding.year.getText().toString().equals(String.valueOf(currentBook.getYear()));

        // Проверка, если любое из значений изменилось
        return isTitleChanged || isAuthorsChanged || isAddressChanged || isPublisherChanged ||
                isPageChanged || isPriceChanged || isCopyChanged || isYearChanged;
    }

    private void setupTextWatchers() {
        // Слушатели на изменение текста для каждого поля
        binding.title.addTextChangedListener(createTextWatcher());
        binding.authors.addTextChangedListener(createTextWatcher());
        binding.address.addTextChangedListener(createTextWatcher());
        binding.publisher.addTextChangedListener(createTextWatcher());
        binding.page.addTextChangedListener(createTextWatcher());
        binding.price.addTextChangedListener(createTextWatcher());
        binding.copy.addTextChangedListener(createTextWatcher());
        binding.year.addTextChangedListener(createTextWatcher());
    }

    /**
     * Создание TextWatcher для отслеживания изменений
     */
    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Не используется
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Не используется
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Проверка изменений после изменения текста
                binding.saveButton.setEnabled(checkIfBookDataChanged());
            }
        };
    }


    private void saveBook() {
        viewModel.updateBook(
                binding.title.getText().toString(),
                binding.authors.getText().toString(),
                binding.address.getText().toString(),
                binding.publisher.getText().toString(),
                Integer.parseInt(binding.page.getText().toString()),
                Float.parseFloat(binding.price.getText().toString()),
                Integer.parseInt(binding.copy.getText().toString()),
                Integer.parseInt(binding.year.getText().toString())
        );
    }
}
