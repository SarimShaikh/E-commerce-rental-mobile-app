package com.ecommerce.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ecommerce.app.R;
import com.ecommerce.app.model.ProductDetails;

import java.util.List;

public class ProductDetailCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ProductDetails> productsItems;

    public ProductDetailCustomListAdapter(Activity activity, List<ProductDetails> productsItems) {
        this.activity = activity;
        this.productsItems = productsItems;
    }

    @Override
    public int getCount() {
        return productsItems.size();
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
            convertView = inflater.inflate(R.layout.list_row_productdetails, null);

        TextView size = (TextView) convertView.findViewById(R.id.size_value);
        TextView fineAmount = (TextView) convertView.findViewById(R.id.fineAmount_value);
        TextView price = (TextView) convertView.findViewById(R.id.price_value);
        TextView rentalDays = (TextView) convertView.findViewById(R.id.rentalDays_value);

        ProductDetails productDetails = productsItems.get(position);
        size.setText(productDetails.getItemSize());
        fineAmount.setText(productDetails.getFineAmount().toString());
        price.setText(productDetails.getItemPrice().toString());
        rentalDays.setText(productDetails.getRentalDays().toString());

        return convertView;
    }
}
