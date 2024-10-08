package com.malivasileva.libraryapp;

import com.malivasileva.data.DatabaseConnection;
import com.malivasileva.libraryapp.model.Reader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class RegRunnable implements Runnable{
    Reader reader;
    RegHandlerThread regHandlerThread;
    Thread thread;

    RegRunnable(RegHandlerThread handlerThread, Reader reader) {
        this.regHandlerThread = handlerThread;
        this.reader = reader;
        thread = new Thread (this);
    }
    @Override
    public void run() {
        try(Connection db = DatabaseConnection.getAuthConnection()) {
            CallableStatement callableStatement = db.prepareCall("{ ? = call register_reader(?, ?, ?, ?) }");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, reader.getName());
            callableStatement.setString(3, reader.getAddress());
            callableStatement.setString(4, reader.getPhone());
            callableStatement.setString(5, reader.getPassword());
            callableStatement.execute();
            long response = callableStatement.getInt(1);
            regHandlerThread.sendResponse(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (!thread.isAlive()) {
            thread.start();
        }
    }
}
