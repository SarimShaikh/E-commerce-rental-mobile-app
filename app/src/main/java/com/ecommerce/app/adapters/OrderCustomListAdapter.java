package com.ecommerce.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ecommerce.app.R;
import com.ecommerce.app.model.Order;

import java.util.List;

public class OrderCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Order> orderList;

    public OrderCustomListAdapter(Activity activity, List<Order> orderList) {
        this.activity = activity;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
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
            convertView = inflater.inflate(R.layout.order_list_row, null);

        TextView itemName = convertView.findViewById(R.id.order_prod_name);
        TextView size = convertView.findViewById(R.id.order_prod_size);
        TextView price = convertView.findViewById(R.id.order_prod_price);
        TextView quantity = convertView.findViewById(R.id.order_prod_quantity);
        TextView orderNo = convertView.findViewById(R.id.order_prod_orderNum);
        TextView orderDate = convertView.findViewById(R.id.order_prod_orderDate);
        TextView fromDate = convertView.findViewById(R.id.order_prod_from);
        TextView toDate = convertView.findViewById(R.id.order_prod_to);

        Order order = orderList.get(position);
        itemName.setText(order.getItemName());
        size.setText(order.getItemSize());
        price.setText(order.getItemPrice().toString());
        quantity.setText(order.getQuantity().toString());
        orderNo.setText(order.getOrderNumber());
        orderDate.setText(order.getOrderDate());
        fromDate.setText(order.getFromDate());
        toDate.setText(order.getToDate());

        return convertView;
    }
}
