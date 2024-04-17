package com.example.libraryapp.reader;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libraryapp.DatabaseConnection;
import com.example.libraryapp.R;
import com.example.libraryapp.databinding.RMainBinding;

import java.sql.Connection;
import java.sql.SQLException;

public class ReaderActivity extends AppCompatActivity {

    RMainBinding binding;
    Connection db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            db = DatabaseConnection.getReaderConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }

        binding = RMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new rMainFragment());
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            long id = item.getItemId();
            long lend = R.id.item_r_lend;
            long books = R.id.item_r_books;
            long profile = R.id.item_r_profile;

            if (id == lend) replaceFragment(new rLendingsFragment());
            else if (id == books) replaceFragment(new rBooksFragment());
            else if (id == profile) replaceFragment(new rProfileFragment());

            return true;
        });
    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.r_frame, fragment);
        fragmentTransaction.commit();
    }
}
