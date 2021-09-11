package com.ecommerce.app.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ecommerce.app.R;
import com.ecommerce.app.adapters.StoresCustomListAdapter;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.model.Stores;
import com.ecommerce.app.singletonvolley.VolleySingleton;
import com.ecommerce.app.storage.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoresActivity extends AppCompatActivity {

    // Log tag
    private static final String TAG = StoresActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<Stores> storesList = new ArrayList<Stores>();
    private ListView listView;
    private EditText searchStore;
    private StoresCustomListAdapter adapter;
    /*private AppBarConfiguration mAppBarConfiguration;*/
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    public static long storeId;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = (NavigationView) findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        //Toast.makeText(getApplicationContext(),"Cart panel is open",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.orders:
                        Intent intent1 = new Intent(getApplicationContext(), OrderActivity.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout:
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });


        listView = (ListView) findViewById(R.id.list);
        adapter = new StoresCustomListAdapter(this, storesList);
        listView.setAdapter(adapter);
        searchStore = findViewById(R.id.store_search);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonArrayRequest storesReq = new JsonArrayRequest(URLS.URL_GET_STORES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Stores stores = new Stores();
                                stores.setStoreId(Long.parseLong(obj.getString("storeId")));
                                stores.setUserId(Long.parseLong(obj.getString("userId")));
                                stores.setStoreName(obj.getString("storeName"));
                                stores.setStoreContact(obj.getString("storeContact"));
                                stores.setStoreAddress(obj.getString("storeAddress"));
                                stores.setImageUri(obj.getString("imagePath"));

                                // adding movie to movies array
                                storesList.add(stores);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        storesReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        VolleySingleton.getInstance2().addToRequestQueue(storesReq);

        //for onclick on list view item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stores stores = (Stores) parent.getItemAtPosition(position);
                storeId = stores.getStoreId();
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });

        searchStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //StoresActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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

}
