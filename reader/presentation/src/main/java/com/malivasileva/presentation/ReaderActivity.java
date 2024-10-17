package com.malivasileva.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.presentation.databinding.RMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReaderActivity extends AppCompatActivity {

    RMainBinding binding;

    private ReaderViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ReaderViewModel.class);
        viewModel.getLendings();
        viewModel.getCurrentLendings();

        binding = RMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new rMainFragment());
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            long id = item.getItemId();
            long main = R.id.item_r_main;
            long lend = R.id.item_r_lend;
            long books = R.id.item_r_books;
            long profile = R.id.item_r_profile;

            if (id == lend) replaceFragment(new rLendingsFragment());
            else if (id == main) replaceFragment(new rMainFragment());
            else if (id == books) replaceFragment(new rBooksFragment());
            else if (id == profile) replaceFragment(new rProfileFragment());

            return true;
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEventLiveData().observe(this, event -> {
            if (event != null) {
                Toast.makeText(this, event, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.r_frame, fragment);
        fragmentTransaction.commit();
    }
}
