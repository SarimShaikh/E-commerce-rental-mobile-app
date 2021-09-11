package com.ecommerce.app.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce.app.R;
import com.ecommerce.app.adapters.CartCustomListAdapter;

public class CartActivity extends AppCompatActivity {

    private ListView listView;
    private CartCustomListAdapter cartCustomListAdapter;
    TextView textViewTotal;
    private Button checkOutBtn;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        checkOutBtn = findViewById(R.id.buttonCart);
        textViewTotal = findViewById(R.id.totalAmount);
        listView = (ListView) findViewById(R.id.cart_list);
        cartCustomListAdapter = new CartCustomListAdapter(this);
        listView.setAdapter(cartCustomListAdapter);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!CartCustomListAdapter.cartItemsList.isEmpty()) {
                                    Long totalAmount = CartCustomListAdapter.cartItemsList.stream().mapToLong(elem -> elem.getPrice()).sum();
                                    textViewTotal.setText(totalAmount.toString());
                                }else{
                                    textViewTotal.setText("0");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                intent.putExtra("paidAmount",textViewTotal.getText());
                startActivity(intent);
            }
        });
    }
}
