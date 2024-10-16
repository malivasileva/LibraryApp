package com.malivasileva.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class rProfileFragment extends Fragment {

    private EditText cardEt;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText addressEt;
    private ToggleButton editNameButton;
    private ToggleButton editPhoneButton;
    private ToggleButton editAddressButton;
    private Button deleteButton;
    private Button exitButton;
    private ReaderViewModel viewModel;

    private String initialName;
    private String initialPhone;
    private String initialAddress;


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
        editNameButton = view.findViewById(R.id.edit_name_btn);
        editPhoneButton = view.findViewById(R.id.edit_phone_btn);
        editAddressButton = view.findViewById(R.id.edit_address_btn);
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


        /*// Наблюдение за errorLiveData
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                // Показ ошибки пользователю (например, через Toast)
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEventLiveData().observe(getViewLifecycleOwner(), event -> {
            if (event != null) {
                Toast.makeText(requireContext(), event, Toast.LENGTH_SHORT).show();
            }
        });*/

        editNameButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nameEt.setEnabled(isChecked);
            }
        });

        editPhoneButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                phoneEt.setEnabled(isChecked);
            }
        });

        editAddressButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addressEt.setEnabled(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        initialName = nameEt.getText().toString();
        initialPhone = phoneEt.getText().toString();
        initialAddress = addressEt.getText().toString();
    }

    @Override
    public void onPause() {
        super.onPause();

        String currentName = nameEt.getText().toString();
        String currentPhone = phoneEt.getText().toString();
        String currentAddress = addressEt.getText().toString();

        if (!currentName.equals(initialName) || !currentPhone.equals(initialPhone) || !currentAddress.equals(initialAddress)) {

            new AlertDialog.Builder(requireContext())
                    .setTitle("Изменение данных профиля")
                    .setMessage("Вы хотите сохранить изменения?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            viewModel.updateProfile(currentName, currentPhone, currentAddress);
                        }
                    })
                    .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(requireContext(), "Изменения отменены", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
//                    viewModel.updateProfile(currentName, currentPhone, currentAddress);
        }
    }
}