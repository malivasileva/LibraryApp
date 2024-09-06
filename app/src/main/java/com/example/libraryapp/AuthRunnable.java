package com.example.libraryapp;

import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class AuthRunnable implements Runnable{
    public final static int LIBRARIAN = 1;
    public final static int READER = 2;
    public Thread thread;
    private AuthHandlerThread authHandlerThread;
    private Long login;
    private String password;
    private int type;

    AuthRunnable (AuthHandlerThread handlerThread, int type, Long login, String password) {
        authHandlerThread = handlerThread;
        thread = new Thread (this);
        this.login = login;
        this.password = password;
        this.type = type;
    }
    @Override
    public void run() {
//        System.out.println("runnable start "  + Thread.currentThread().getName());
        if (type == LIBRARIAN) {
            authLib();
        } else if (type == READER) {
            authReader();
        }
//        System.out.println("runnable finished"  + Thread.currentThread().getName());

    }

    private void authLib() {
        try (Connection db = DatabaseConnection.getAuthConnection()){
            CallableStatement callableStatement = db.prepareCall("{ ? = call authenticate_librarian(?, ?) }");
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            callableStatement.setLong(2, login);
            callableStatement.setString(3, password);
            callableStatement.execute();
            boolean isAuthenticated = callableStatement.getBoolean(1);
            authHandlerThread.sendResponse(isAuthenticated);
        } catch (SQLException e) {
            System.out.println("PEACH");
            e.printStackTrace();
        }
    }
    private void authReader() {
        try (Connection db = DatabaseConnection.getAuthConnection()){
                CallableStatement callableStatement = db.prepareCall("{ ? = call authenticate_reader(?, ?) }");
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            callableStatement.setLong(2, login);
            callableStatement.setString(3, password);
            callableStatement.execute();
            boolean isAuthenticated = callableStatement.getBoolean(1);
            authHandlerThread.sendResponse(isAuthenticated);
        } catch (SQLException e) {
            System.out.println("MANGO");
            e.printStackTrace();
        }
    }

    public void start() {
        if (!thread.isAlive()) {
            thread.start();
        }
    }
}
