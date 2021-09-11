package com.ecommerce.app.activites;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ecommerce.app.R;
import com.ecommerce.app.adapters.CartCustomListAdapter;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.messageDto.LoginResponse;
import com.ecommerce.app.singletonvolley.VolleySingleton;
import com.ecommerce.app.storage.SharedPrefManager;
import com.ecommerce.app.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity {

    private TextView textViewPaidAmount;
    private ProgressDialog pDialog;
    private Button buttonPlaceOrder;
    private static String TAG = PlaceOrderActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);
        textViewPaidAmount = findViewById(R.id.paidAmount);
        buttonPlaceOrder = findViewById(R.id.buttonOrder);

        textViewPaidAmount.setText(getIntent().getStringExtra("paidAmount"));
        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        LoginResponse loggedUser = SharedPrefManager.getInstance(this).getUser();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, Object> postParam = getStringObjectMap(loggedUser);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URLS.URL_PLACE_ORDER, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.get("responseCode").equals("200")) {
                                hideProgressDialog();

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this);
                                alertDialog.setTitle("Order Placed Successfully!");
                                alertDialog.setMessage("Your order no: " + response.get("orderNo") + "\nCollect your order within two days otherwise it will be discarded");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        CartCustomListAdapter.cartItemsList.clear();
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                alertDialog.show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + loggedUser.getJwtToken());
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(LoginResponse loggedUser) {
        JSONObject requestJson = new JSONObject();
        Map<String, Object> postParam = new HashMap<>();
        try {
            requestJson.put("paymentType", "Credit Card");
            requestJson.put("amount", Long.parseLong(textViewPaidAmount.getText().toString()));
            postParam.put("userId", loggedUser.getUserId());
            postParam.put("storeId", StoresActivity.storeId);
            postParam.put("paymentDTO", requestJson);
            postParam.put("orderDetailDTO", Utils.CartListToJsonArray(CartCustomListAdapter.cartItemsList));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return postParam;
    }

    private void hideProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
