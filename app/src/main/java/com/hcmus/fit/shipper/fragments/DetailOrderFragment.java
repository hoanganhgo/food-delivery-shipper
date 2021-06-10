package com.hcmus.fit.shipper.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.models.Address;
import com.hcmus.fit.shipper.models.DishOrder;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.util.AppUtil;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailOrderFragment extends Fragment {
    private final int title;
    private final OrderModel orderModel;

    public DetailOrderFragment(int title, OrderModel orderModel) {
        this.title = title;
        this.orderModel = orderModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("order", "onCreate Place Holder");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ImageButton btnGGMap = root.findViewById(R.id.btn_gg_map);
        TextView tvObjectName = root.findViewById(R.id.tv_object_name);
        TextView tvObjectAddress = root.findViewById(R.id.tv_object_address);
        TextView tvPrice = root.findViewById(R.id.tv_price);
        LinearLayout lnOrder = root.findViewById(R.id.ln_order);

        btnGGMap.setOnClickListener(v -> {
            // Create a Uri from an intent string. Use the result to create an Intent.
            Address addressDirection = null;
            if (title == R.string.customer) {
                addressDirection = orderModel.getCustomerAddress();
            } else {
                addressDirection = orderModel.getMerchantAddress();
            }

            String ggMapUri = "http://maps.google.com/maps?saddr=" + ShipperInfo.getInstance().getLatitude()
                    + "," + ShipperInfo.getInstance().getLongitude() + "&daddr="
                    + addressDirection.getLatitude() + "," +addressDirection.getLongitude();
            Uri gmmIntentUri = Uri.parse(ggMapUri);

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        });

        if (this.title == R.string.merchant) {
            tvObjectName.setText(orderModel.getMerchant());
            tvObjectAddress.setText(orderModel.getMerchantAddress().getFullAddress());
            tvPrice.setText(getResources().getString(R.string.pay_money)
                    + AppUtil.convertCurrency(orderModel.getSubTotal()));
            //tvTotal.setText(AppUtil.convertCurrency(orderModel.getSubTotal()));
        } else {
            tvObjectName.setText(orderModel.getCustomer());
            tvObjectAddress.setText(orderModel.getCustomerAddress().getFullAddress());
            tvPrice.setText(getResources().getString(R.string.get_order)
                    + AppUtil.convertCurrency(orderModel.getTotal()));
            //tvTotal.setText(AppUtil.convertCurrency(orderModel.getTotal()));
        }

        for (int i = 0; i < orderModel.getDishOrderList().size(); i++) {
            //LayoutInflater inflaterDish = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.widget_dish, null);
            TextView tvDishName = row.findViewById(R.id.tv_dish_name);
            TextView tvQuantity = row.findViewById(R.id.tv_quantity);
            TextView tvPriceDish = row.findViewById(R.id.tv_dish_price);
            TextView tvTotalOrder = row.findViewById(R.id.tv_total);
            TextView tvOptionLabel = row.findViewById(R.id.tv_option_label);
            TextView tvOptions = row.findViewById(R.id.tv_options);

            DishOrder dishOrder = orderModel.getDishOrderList().get(i);
            tvDishName.setText((i + 1) + ". " + dishOrder.dishModel.getName());
            tvQuantity.setText(String.valueOf(dishOrder.num));
            tvPriceDish.setText(AppUtil.convertCurrency(dishOrder.dishModel.getPrice()));
            tvTotalOrder.setText(AppUtil.convertCurrency(dishOrder.getTotalPrice()));
            tvOptions.setText(dishOrder.dishModel.getOptions());
            if (dishOrder.dishModel.getOptions().isEmpty()) {
                tvOptionLabel.setVisibility(View.GONE);
                tvOptions.setVisibility(View.GONE);
            }

            lnOrder.addView(row);
        }


        return root;
    }

    public int getTitle() {
        return title;
    }
}