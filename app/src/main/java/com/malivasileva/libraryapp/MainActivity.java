package com.malivasileva.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.malivasileva.presentation.LibrarianActivity;
import com.malivasileva.presentation.ReaderActivity;


import java.sql.*;

public class MainActivity extends AppCompatActivity {
    Connection db;
    UiHandler uiHandler;
    AuthHandlerThread handlerThread;
    AuthRunnable runnable;
    //Boolean isAuthed = false;

    EditText etLogin;
    EditText etPassword;
    Button btnLogin;
    Button btnReg;
    ToggleButton toggleBtn;
    TextView errorMsg;
//    Button button;
    long login;
    String password;
    Class classDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        uiHandler = new UiHandler();

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_reg);
        toggleBtn = findViewById(R.id.toggle);
        errorMsg = findViewById(R.id.error_msg);

        /*button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classDestination = LibrarianActivity.class;
                login = 1111l;
                goToActivity();
            }
        });*/

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnReg.setVisibility(View.GONE);
                } else {
                    btnReg.setVisibility(View.VISIBLE);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = Long.parseLong(String.valueOf(etLogin.getText()));
                password = String.valueOf(etPassword.getText());
                int type = 0;
                if (toggleBtn.isChecked()) { // Сотрудник
                    type = AuthRunnable.LIBRARIAN;
                    classDestination = LibrarianActivity.class;
                } else { // Читатель
                    type = AuthRunnable.READER;
                    classDestination = ReaderActivity.class;
                }
                authenticateUser(type, login, password);
//                if (isAuthed) goToActivity(classDestination, login);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void authenticateUser(int type, long login, String password) {
        handlerThread = new AuthHandlerThread(uiHandler);
        handlerThread.start();
        runnable = new AuthRunnable(handlerThread, type, login, password);
        runnable.start();
        try {
            runnable.thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void goToActivity() {
        Bundle bundle = new Bundle();
        bundle.putLong("login", login);
        Intent intent = new Intent(MainActivity.this, classDestination);
        startActivity(intent, bundle);
        finish();
    }

    class UiHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            boolean isAuthed = (boolean) msg.obj;
            Toast.makeText(MainActivity.this, Boolean.toString(isAuthed), Toast.LENGTH_SHORT).show();
            if (isAuthed) goToActivity();
            else Toast.makeText(MainActivity.this, "Неверный логин или пароль :(", Toast.LENGTH_SHORT).show();
        }
    }

}