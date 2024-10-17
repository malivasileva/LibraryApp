package com.malivasileva.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefUserStorage implements UserStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String USER_ID = "user_id";
//    private static final String SHARED_PREFS_NAME = "shared_prefs_name";

    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefUserStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveId(Long id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(USER_ID, id);
        editor.apply();
    }

    @Override
    public Long getId() {
        Long tmp = sharedPreferences.getLong(USER_ID, -1);
        return tmp;
    }

    @Override
    public void deleteId() {
        sharedPreferences.edit().clear();
    }
}
