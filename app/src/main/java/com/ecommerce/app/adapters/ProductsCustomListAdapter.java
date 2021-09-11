package com.ecommerce.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ecommerce.app.R;
import com.ecommerce.app.model.Products;
import com.ecommerce.app.singletonvolley.VolleySingleton;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
public class ProductsCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Products> productsItems;
    ImageLoader imageLoader = VolleySingleton.getInstance2().getImageLoader();

    public ProductsCustomListAdapter(Activity activity, List<Products> productsItems) {
        this.activity = activity;
        this.productsItems = productsItems;
    }

    @Override
    public int getCount() {
        return productsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return productsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_products, null);

        if (imageLoader == null)
            imageLoader = VolleySingleton.getInstance2().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.productName);
        TextView price = (TextView) convertView.findViewById(R.id.itemPrice);

        // getting product data for the row
        Products items = productsItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(items.getImagesList().get(0).getImagePath(), imageLoader);
        title.setText(items.getItemName());
        price.setText(items.getProductDetailsList().get(0).getItemPrice().toString());

        return convertView;
    }
}
