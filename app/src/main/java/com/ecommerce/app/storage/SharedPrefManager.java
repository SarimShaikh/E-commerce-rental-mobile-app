package com.ecommerce.app.storage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ecommerce.app.activites.LoginActivity;
import com.ecommerce.app.messageDto.LoginResponse;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_USERNAME = "keyUserName";
    private static final String KEY_USERID = "keyUserId";
    private static final String KEY_AUTH_TOKEN = "authToken";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, loginResponse.getUserName());
        editor.putLong(KEY_USERID, loginResponse.getUserId());
        editor.putString(KEY_AUTH_TOKEN,loginResponse.getJwtToken());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public LoginResponse getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginResponse(
                sharedPreferences.getLong(KEY_USERID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_AUTH_TOKEN, null)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}

