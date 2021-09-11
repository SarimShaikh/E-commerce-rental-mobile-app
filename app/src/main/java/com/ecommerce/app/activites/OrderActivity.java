package com.ecommerce.app.activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ecommerce.app.R;
import com.ecommerce.app.adapters.OrderCustomListAdapter;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.messageDto.LoginResponse;
import com.ecommerce.app.model.Order;
import com.ecommerce.app.singletonvolley.VolleySingleton;
import com.ecommerce.app.storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = OrderActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Order> orderList = new ArrayList<>();
    private ListView listView;
    private OrderCustomListAdapter orderCustomListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView = (ListView) findViewById(R.id.order_list);
        orderCustomListAdapter = new OrderCustomListAdapter(this, orderList);
        listView.setAdapter(orderCustomListAdapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        LoginResponse loggedUser = SharedPrefManager.getInstance(this).getUser();

        JsonArrayRequest storesReq = new JsonArrayRequest(URLS.URL_GET_RENTAL_ITEMS+loggedUser.getUserId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Order order = new Order();
                                order.setItemName(obj.getString("itemName"));
                                order.setItemSize(obj.getString("itemSize"));
                                order.setItemPrice(Long.parseLong(obj.getString("itemPrice")));
                                order.setOrderNumber(obj.getString("orderNumber"));
                                order.setQuantity(Integer.parseInt(obj.getString("quantity")));
                                order.setOrderDate(obj.getString("orderDate"));
                                order.setToDate(obj.getString("toDate"));
                                order.setFromDate(obj.getString("fromDate"));
                                // adding movie to movies array
                                orderList.add(order);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        orderCustomListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + loggedUser.getJwtToken());
                return headers;
            }
        };
        storesReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        VolleySingleton.getInstance2().addToRequestQueue(storesReq);

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
