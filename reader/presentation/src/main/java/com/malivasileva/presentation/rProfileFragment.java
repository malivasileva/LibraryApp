package com.malivasileva.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class rProfileFragment extends Fragment {

    private EditText cardEt;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText addressEt;
    private Button deleteButton;
    private Button exitButton;
    private ReaderViewModel viewModel;


    public rProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReaderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_profile, container, false);

        cardEt = view.findViewById(R.id.profile_et_card);
        nameEt = view.findViewById(R.id.profile_et_name);
        phoneEt = view.findViewById(R.id.profile_et_phone);
        addressEt = view.findViewById(R.id.profile_et_address);
        exitButton = view.findViewById(R.id.profile_exit_btn);
        deleteButton = view.findViewById(R.id.profile_delete_btn);


        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), profile -> {
                    if (profile != null) {
                        cardEt.setText(String.valueOf(profile.getCard()));
                        nameEt.setText(profile.getName());
                        phoneEt.setText(profile.getPhone());
                        addressEt.setText(profile.getAddress());
                    }
                });


        // Наблюдение за errorLiveData
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                // Показ ошибки пользователю (например, через Toast)
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}