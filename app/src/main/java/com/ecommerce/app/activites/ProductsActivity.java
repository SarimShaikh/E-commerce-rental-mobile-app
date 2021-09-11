package com.ecommerce.app.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ecommerce.app.R;
import com.ecommerce.app.adapters.ProductsCustomListAdapter;
import com.ecommerce.app.adapters.RecyclerViewAdapter;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.model.Categories;
import com.ecommerce.app.model.Images;
import com.ecommerce.app.model.ProductDetails;
import com.ecommerce.app.model.Products;
import com.ecommerce.app.singletonvolley.VolleySingleton;
import com.ecommerce.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {

    private static final String TAG = ProductsActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Categories> categoriesList = new ArrayList<>();
    private List<Products> productsList = new ArrayList<Products>();
    private GridView gridView;
    private ProductsCustomListAdapter adapter;
    private View view;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    RelativeLayout relative1;
    public static String productSharedName="";

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridView = (GridView) findViewById(R.id.gridView);
        //relative1 = (RelativeLayout) findViewById(R.id.relative1);
        adapter = new ProductsCustomListAdapter(this, productsList);
        gridView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        getCategories();

        // Creating volley request obj
        JsonObjectRequest productsReq = new JsonObjectRequest(Request.Method.GET, URLS.URL_GET_STORE_ITEMS + "?storeId=" + StoresActivity.storeId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
                        Log.d(TAG, response.toString());

                        try {
                            JSONArray itemsArray = response.getJSONArray("items");

                            for (int i = 0; i < itemsArray.length(); i++) {
                                JSONObject obj = itemsArray.getJSONObject(i);
                                Products products = new Products();
                                products.setItemId(Long.parseLong(obj.getString("itemId")));
                                products.setStoreId(Long.parseLong(obj.getString("storeId")));
                                products.setCategoryId(Long.parseLong(obj.getString("categoryId")));
                                products.setItemName(obj.getString("itemName"));
                                products.setImagesList(Utils.convertImageList(obj.getJSONArray("images")));
                                products.setProductDetailsList(Utils.convertProductDetailList(obj.getJSONArray("itemDetails")));
                                productsList.add(products);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Volley error" + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        productsReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        VolleySingleton.getInstance2().addToRequestQueue(productsReq);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductsActivity.this, ProductDetailsActivity.class);
                productSharedName = productsList.get(position).getItemName();
                ArrayList<Images> imagesList = (ArrayList<Images>) productsList.get(position).getImagesList();
                ArrayList<ProductDetails> productDetails = (ArrayList<ProductDetails>) productsList.get(position).getProductDetailsList();
                intent.putExtra("IMAGE_LIST", imagesList);
                intent.putExtra("PRODUCT_DETAILS", productDetails);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "This is item in position " + productsList.get(position).getItemName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void getCategories() {
        JsonObjectRequest productsReq = new JsonObjectRequest(Request.Method.GET, URLS.URL_GET_CATEGORIES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                            JSONArray itemsArray = response.getJSONArray("categories");

                            for (int i = 0; i < itemsArray.length(); i++) {
                                JSONObject obj = itemsArray.getJSONObject(i);
                                Categories categories = new Categories();
                                categories.setCategoryId(Long.parseLong(obj.getString("categoryId")));
                                categories.setCategoryType(obj.getString("categoryType"));
                                categoriesList.add(categories);
                            }
                            initRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        productsReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        VolleySingleton.getInstance2().addToRequestQueue(productsReq);
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(categoriesList, this);
        adapter1.notifyDataSetChanged();
        recyclerView.setAdapter(adapter1);
    }

}
