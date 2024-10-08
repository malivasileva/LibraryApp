package com.malivasileva.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.malivasileva.presentation.databinding.RMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReaderActivity extends AppCompatActivity {

    RMainBinding binding;

//    @Inject
//    ViewModelProvider.Factory viewModelFactory;
    private ReaderViewModel readerViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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

//        readerViewModel = new ViewModelProvider(this).get(ReaderViewModel.class);



    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.r_frame, fragment);
        fragmentTransaction.commit();
    }
}
