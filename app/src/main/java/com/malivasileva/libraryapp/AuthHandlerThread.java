package com.malivasileva.libraryapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class AuthHandlerThread extends HandlerThread {
    private MainActivity.UiHandler uiHandler;
    private Handler handler;
    public AuthHandlerThread(String name) {
        super(name);
    }

    public AuthHandlerThread(MainActivity.UiHandler uiHandler) {
        super("AuthHandlerThread");
        this.uiHandler = uiHandler;
    }
    public Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getLooper());
        }
        return handler;
    }

    private Handler getHandler(Looper looper) {
        return new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                System.out.println("handleMessage of AuthHandlerThread "  + Thread.currentThread().getName());
                boolean value = (boolean) msg.obj;
                Message message = Message.obtain(); // Create a new Message instance
                message.what = 0; // Set the message type (what) to differentiate messages if needed
                message.obj = value; // Set the boolean value as the object payload
                handler.sendMessage(message); // Send the message
                uiHandler.sendMessage(msg);
            }
        };
    }

    public void sendResponse(boolean response){
//        System.out.println("sendResponse of AuthHandlerThread "  + Thread.currentThread().getName());
        Message message = new Message();
        message.obj = response;
        //handler.sendMessage(message);
        uiHandler.sendMessage(message);
    }
    protected void onLooperPrepared() {
        super.onLooperPrepared();
//        System.out.println("onLooperPrepared of AuthHandlerThread "  + Thread.currentThread().getName());
        handler = getHandler(getLooper());
    }
}
