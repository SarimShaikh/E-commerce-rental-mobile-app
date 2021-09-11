package com.ecommerce.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce.app.activites.LoginActivity;
import com.ecommerce.app.activites.StoresActivity;
import com.ecommerce.app.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), StoresActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}