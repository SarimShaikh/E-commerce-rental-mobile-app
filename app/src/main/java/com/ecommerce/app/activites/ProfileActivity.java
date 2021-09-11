package com.ecommerce.app.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce.app.R;
import com.ecommerce.app.messageDto.LoginResponse;
import com.ecommerce.app.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewId, textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        //getting the current user
        LoginResponse loggedUser = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(loggedUser.getUserId()));
        textViewUsername.setText(loggedUser.getUserName());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}