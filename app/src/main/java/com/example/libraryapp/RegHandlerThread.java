package com.example.libraryapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class RegHandlerThread extends HandlerThread {
    SignupActivity.UiHandler uiHandler;
    Handler handler;
    public RegHandlerThread(String name) {
        super(name);
    }

    public RegHandlerThread(SignupActivity.UiHandler uiHandler) {
        super("RegHandlerThread");
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
                long value = (long) msg.obj;
                Message message = Message.obtain(); // Create a new Message instance
                message.what = 0; // Set the message type (what) to differentiate messages if needed
                message.obj = value; // Set the boolean value as the object payload
                handler.sendMessage(message); // Send the message
                uiHandler.sendMessage(msg);
            }
        };
    }
    public void sendResponse(long response){
        Message message = new Message();
        message.obj = response;
        uiHandler.sendMessage(message);
    }
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = getHandler(getLooper());
    }
}
