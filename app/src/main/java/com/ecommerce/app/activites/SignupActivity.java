package com.ecommerce.app.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ecommerce.app.MainActivity;
import com.ecommerce.app.R;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.messageDto.CustomResponseDTO;
import com.ecommerce.app.singletonvolley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText editTextUsername, editTextEmail, editTextPassword, editTextContact, editTextAddress;
    RadioGroup radioGroupGender;
    ProgressBar progressBar;
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String contact = editTextContact.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextContact.setError("Enter Mobile Number");
            editTextContact.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            editTextAddress.setError("Enter Address");
            editTextAddress.requestFocus();
            return;
        }

        List<String> roles = new ArrayList<>();
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();
        JSONObject requestJson = new JSONObject();
        try {
            roles.add("user");
            requestJson.put("username", username);
            requestJson.put("email", email);
            requestJson.put("password", password);
            requestJson.put("contact", contact);
            requestJson.put("address", address);
            requestJson.put("role", new JSONArray(roles));
            requestJson.put("isCustomer", "Y");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.d(TAG, requestJson.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLS.URL_REGISTER, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, response.toString());
                        try {
                            Log.i(TAG, String.valueOf(response));

                            customResponseDTO.setResponseCode(response.getString("responseCode"));
                            customResponseDTO.setMessage(response.getString("message"));
                            if (customResponseDTO.getResponseCode().equals("200")) {
                                Toast.makeText(getApplicationContext(), customResponseDTO.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            } else if (customResponseDTO.getResponseCode().equals("208")) {
                                Toast.makeText(getApplicationContext(), customResponseDTO.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}