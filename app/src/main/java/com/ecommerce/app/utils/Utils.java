package com.ecommerce.app.utils;

import android.widget.TextView;

import com.ecommerce.app.model.CartItems;
import com.ecommerce.app.model.Images;
import com.ecommerce.app.model.ProductDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static ArrayList<Images> convertImageList(JSONArray jsonArray) {
        ArrayList<Images> imagesList = new ArrayList<Images>();
        try {
            for (int i = 0, l = jsonArray.length(); i < l; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String imgPath = jsonObject.getString("imagePath").replaceFirst("http://localhost:8082", "http://192.168.100.8:8082");
                Images images = new Images(jsonObject.getLong("imageId"), imgPath);
                imagesList.add(images);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imagesList;
    }

    public static List<ProductDetails> convertProductDetailList(JSONArray jsonArray) {
        List<ProductDetails> productDetailsList = new ArrayList<>();
        try {
            for (int i = 0, l = jsonArray.length(); i < l; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProductDetails productDetails = new ProductDetails(
                        jsonObject.getLong("itemId"),
                        jsonObject.getLong("itemDetailId"),
                        jsonObject.getString("itemSize"),
                        jsonObject.getLong("itemPrice"),
                        jsonObject.getLong("fineAmount"),
                        jsonObject.getLong("rentalDays"));
                productDetailsList.add(productDetails);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return productDetailsList;
    }

    public static JSONArray CartListToJsonArray(List<CartItems> cartItemsList) {
        JSONArray orderDetailJsonArray = new JSONArray();
        for (CartItems cartItems : cartItemsList) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("itemId", cartItems.getItemId());
                jsonObject.put("itemDetailId", cartItems.getItemDetailId());
                jsonObject.put("quantity", cartItems.getQuantity());
                jsonObject.put("price", cartItems.getPrice());
                jsonObject.put("fromDate", cartItems.getFromDate());
                jsonObject.put("toDate",cartItems.getToDate());
                orderDetailJsonArray.put(jsonObject);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return orderDetailJsonArray;
    }

    public static Boolean isValidateDate(TextView textView) {
        boolean isValidate = false;
        if (textView.getText().toString().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$"))
            isValidate = true;
        return isValidate;
    }
}

