package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.model.Reader;
import com.example.libraryapp.reader.ReaderActivity;

import java.sql.Connection;
import java.sql.SQLException;

public class SignupActivity extends AppCompatActivity {
    EditText etName;
    EditText etAddress;
    EditText etPhone;
    EditText etPassword;
    EditText checkPassword;
    Button btnRegistrate;
    TextView error;
    Long login;
    RegRunnable runnable;
    UiHandler uiHandler;
    RegHandlerThread handlerThread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        checkPassword = findViewById(R.id.et_check);
        btnRegistrate = findViewById(R.id.registrate);
        error = findViewById(R.id.pass_error);

        uiHandler = new SignupActivity.UiHandler();
        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1 = String.valueOf(etPassword.getText());
                String password2 = String.valueOf(checkPassword.getText());

                if (!password1.equals(password2)) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    String name = String.valueOf(etName.getText());
                    String address = String.valueOf(etAddress.getText());
                    String phone = String.valueOf(etPhone.getText());


                    registrate(new Reader(name, address, phone, password1));
                }
            }
        });

    }

    public void registrate(Reader reader) {
        handlerThread = new RegHandlerThread(uiHandler);
        handlerThread.start();
        runnable = new RegRunnable(handlerThread, reader);
        runnable.start();
        try {
            runnable.thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToActivity() {
        Bundle bundle = new Bundle();
        bundle.putLong("login", login);
        Intent intent = new Intent(SignupActivity.this, ReaderActivity.class);
        startActivity(intent, bundle);
        finish();
    }

    class UiHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            long response = (long) msg.obj;
            login = response;
            if (response != 0) {
                Toast.makeText(SignupActivity.this, "Вы успешно зарегистрированы!", Toast.LENGTH_LONG).show();
                goToActivity();
            } else {
                Toast.makeText(SignupActivity.this, "Что-то пошло не так... Возможно, уже существует пользователь с данным номером", Toast.LENGTH_LONG).show();
            }
        }
    }
}
