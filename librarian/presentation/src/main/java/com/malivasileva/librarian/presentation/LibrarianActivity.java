package com.malivasileva.librarian.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.presentation.R;
import com.malivasileva.presentation.databinding.LMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LibrarianActivity extends AppCompatActivity {
    LMainBinding binding;
    LibrarianViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LibrarianViewModel.class);

        binding = LMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new lBooksFragment()); //todo
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            long id = item.getItemId();
            long books = R.id.item_l_book;
            long specialty = R.id.item_l_specialty;
            long lend = R.id.item_l_lendings;
            long readers = R.id.item_l_readers;

            if (id == lend) replaceFragment(new lLendingsFragment());
            else if (id == books) replaceFragment(new lBooksFragment());
            else if (id == specialty) replaceFragment(new lSpecialtyFragment());
            else if (id == readers) replaceFragment(new lReadersFragment());


            return true;
        });


        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.exit) {
                new AlertDialog.Builder(this)
                        .setTitle("Выйти из профиля?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                backToSignIn();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

                return true;
            }
            return false;
        });
    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.l_frame, fragment);
        fragmentTransaction.commit();
    }

    private void backToSignIn() {
        viewModel.exit();
        Intent intent = new Intent();
        intent.setClassName(this.getPackageName(), "com.malivasileva.libraryapp.MainActivity"); // Полное имя класса активности
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();
    }
}