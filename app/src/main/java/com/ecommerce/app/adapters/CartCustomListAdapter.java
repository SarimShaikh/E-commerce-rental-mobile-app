package com.ecommerce.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.app.R;
import com.ecommerce.app.activites.CartActivity;
import com.ecommerce.app.model.CartItems;

import java.util.ArrayList;
import java.util.List;

public class CartCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    public static List<CartItems> cartItemsList = new ArrayList<>();
    public static Long totalAmount = (long) 0;

    public CartCustomListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cartItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_cart_list_row, null);

        ImageView deleteItem = (ImageView) convertView.findViewById(R.id.delete_button);
        TextView productName = (TextView) convertView.findViewById(R.id.cart_prod_name);
        TextView productSize = (TextView) convertView.findViewById(R.id.cart_prod_size);
        TextView productPrice = (TextView) convertView.findViewById(R.id.cart_prod_price);
        TextView productQuantity = (TextView) convertView.findViewById(R.id.cart_prod_quantity);
        TextView productFrom = (TextView) convertView.findViewById(R.id.cart_prod_from);
        TextView productTo = (TextView) convertView.findViewById(R.id.cart_prod_to);

        CartItems cartItems = cartItemsList.get(position);
        productName.setText(cartItems.getProductName());
        productPrice.setText(cartItems.getPrice().toString());
        productSize.setText(cartItems.getSize());
        productQuantity.setText(cartItems.getQuantity().toString());
        productFrom.setText(cartItems.getFromDate());
        productTo.setText(cartItems.getToDate());
        notifyDataSetChanged();

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity cartActivity = new CartActivity();
                Toast.makeText(activity, "Item Removed", Toast.LENGTH_SHORT).show();
                cartItemsList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
