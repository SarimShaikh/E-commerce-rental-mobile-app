package com.ecommerce.app.activites;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ecommerce.app.R;
import com.ecommerce.app.adapters.CartCustomListAdapter;
import com.ecommerce.app.adapters.ProductDetailCustomListAdapter;
import com.ecommerce.app.adapters.ProductDetailsSliderAdapter;
import com.ecommerce.app.customRequest.CustomBooleanRequest;
import com.ecommerce.app.customURL.URLS;
import com.ecommerce.app.model.CartItems;
import com.ecommerce.app.model.Images;
import com.ecommerce.app.model.ProductDetails;
import com.ecommerce.app.singletonvolley.VolleySingleton;
import com.ecommerce.app.utils.Utils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = ProductDetailsActivity.class.getSimpleName();

    private List<ProductDetails> productDetailsList = new ArrayList<>();
    ArrayList<Images> imageList;
    private ListView listView;
    private ProductDetailCustomListAdapter productdetailsAdapter;
    //for slider view
    SliderView sliderView;
    private ProductDetailsSliderAdapter sliderAdapter;
    private TextView fromDate, toDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        DecimalFormat decimalFormat = new DecimalFormat("00");
        listView = (ListView) findViewById(R.id.list);
        sliderView = findViewById(R.id.imageSlider);
        sliderAdapter = new ProductDetailsSliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        imageList = (ArrayList<Images>) getIntent().getSerializableExtra("IMAGE_LIST");
        sliderAdapter.renewItems(imageList);

        productDetailsList = (ArrayList<ProductDetails>) getIntent().getSerializableExtra("PRODUCT_DETAILS");
        productdetailsAdapter = new ProductDetailCustomListAdapter(this, productDetailsList);
        listView.setAdapter(productdetailsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(ProductDetailsActivity.this);
                dialog.setContentView(R.layout.activity_pop_up_window);
                TextView productName = dialog.findViewById(R.id.prod_name);
                TextView prodSize = dialog.findViewById(R.id.prod_size);
                TextView prodPrice = dialog.findViewById(R.id.prod_price);
                TextView quantity = dialog.findViewById(R.id.quntity);
                fromDate = dialog.findViewById(R.id.from_date);
                toDate = dialog.findViewById(R.id.to_date);
                Button addCartButton = dialog.findViewById(R.id.buttonCart);

                productName.setText(ProductsActivity.productSharedName);
                prodSize.setText(productDetailsList.get(position).getItemSize());
                prodPrice.setText(productDetailsList.get(position).getItemPrice().toString());

                addCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!quantity.getText().toString().matches("") && Utils.isValidateDate(fromDate) && Utils.isValidateDate(toDate)) {
                            try {
                                CustomBooleanRequest booleanRequest = new CustomBooleanRequest(0, URLS.URL_CHECK_AVAILABLE_QUANTITY + "?itemDetailId=" + productDetailsList.get(position).getItemDetailId() + "&quantity=" + Long.parseLong(quantity.getText().toString()), "", new Response.Listener<Boolean>() {
                                    @Override
                                    public void onResponse(Boolean response) {
                                        if (response) {
                                            long updatedPrice = Long.parseLong(prodPrice.getText().toString()) * Long.parseLong(quantity.getText().toString());
                                            CartCustomListAdapter.cartItemsList.add(new CartItems(
                                                    productName.getText().toString(),
                                                    productDetailsList.get(position).getItemId(),
                                                    productDetailsList.get(position).getItemDetailId(),
                                                    Long.parseLong(quantity.getText().toString()),
                                                    updatedPrice,
                                                    prodSize.getText().toString(),
                                                    fromDate.getText().toString(),
                                                    toDate.getText().toString()
                                            ));
                                            Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Quantity out of Stock!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(booleanRequest);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please add Quantity and select Date", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(ProductDetailsActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(ProductDetailsActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener2,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + decimalFormat.format(month) + "-" + decimalFormat.format(day);
                fromDate.setText(date);
            }
        };

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + decimalFormat.format(month) + "-" + decimalFormat.format(day);
                toDate.setText(date);
            }
        };
    }
}
