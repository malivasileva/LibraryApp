package com.malivasileva.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.malivasileva.data.DatabaseConnection;
import com.malivasileva.presentation.databinding.LMainBinding;


import java.sql.Connection;
import java.sql.SQLException;

public class LibrarianActivity extends AppCompatActivity {
    Connection db;
    LMainBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            db = DatabaseConnection.getLibrConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }

        binding = LMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new lBooksFragment()); //todo
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            long id = item.getItemId();
            long books = R.id.item_l_book;
            long specialty = R.id.item_l_specialty;
            long lend = R.id.item_l_lendings;
            long readers = R.id.item_l_readers;
            long profile = R.id.item_l_profile;

            if (id == lend) replaceFragment(new lLendingsFragment());
            else if (id == books) replaceFragment(new lBooksFragment());
            else if (id == profile) replaceFragment(new lProfileFragment());
            else if (id == specialty) replaceFragment(new lSpecialtyFragment());
            else if (id == readers) replaceFragment(new lReadersFragment());

            return true;
        });
    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.l_frame, fragment);
        fragmentTransaction.commit();
    }
}
