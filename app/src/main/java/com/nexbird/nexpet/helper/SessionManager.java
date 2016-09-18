package com.nexbird.nexpet.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidNexpet";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_IS_REGITRED_IN = "isRegistredIn";

    private static final String KEY_IS_REGITRED_PET_IN = "isRegistredPetIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setFullRegistred(boolean fullRegistred) {
        editor.putBoolean(KEY_IS_REGITRED_IN, fullRegistred);

        // commit changes
        editor.commit();

        Log.d(TAG, "Registrado!");

    }

    public void setPetRegistred(boolean petRegistred) {
        editor.putBoolean(KEY_IS_REGITRED_PET_IN, petRegistred);

        // commit changes
        editor.commit();

        Log.d(TAG, "Registrado (Pet)!");

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean isRegistredIn() {
        return pref.getBoolean(KEY_IS_REGITRED_IN, false);
    }

    public boolean isPetRegistredIn() {
        return pref.getBoolean(KEY_IS_REGITRED_PET_IN, false);
    }
}