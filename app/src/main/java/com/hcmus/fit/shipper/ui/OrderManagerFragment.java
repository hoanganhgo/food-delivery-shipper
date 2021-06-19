package com.hcmus.fit.shipper.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.OrderPagerAdapter;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.network.MySocket;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrderManagerFragment extends Fragment {

    private ImageButton btnPower;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);
        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        btnPower = root.findViewById(R.id.btn_power);

        viewPager.setAdapter(orderPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        if (ShipperInfo.getInstance().isActive()) {
            btnPower.setBackgroundColor(getResources().getColor(R.color.green_light));
        } else {
            btnPower.setBackgroundColor(getResources().getColor(R.color.red));
        }

        btnPower.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            LayoutInflater inflaterAlert = LayoutInflater.from(getContext());
            View checkInView = inflaterAlert.inflate(R.layout.alert_check_in, null);
            TextView tvTitle = checkInView.findViewById(R.id.tv_check_in_title);
            TextView tvLocation = checkInView.findViewById(R.id.tv_your_location);
            Button btnCancel = checkInView.findViewById(R.id.btn_cancel);
            Button btnConfirm = checkInView.findViewById(R.id.btn_confirm);

            if (ShipperInfo.getInstance().isActive()) {
                tvTitle.setText(getResources().getString(R.string.check_out));
            } else {
                tvTitle.setText(getResources().getString(R.string.check_in));
            }

            double lat = ShipperInfo.getInstance().getLatitude();
            double lng = ShipperInfo.getInstance().getLongitude();

            if (lat == 0 && lng == 0) {
                tvLocation.setText(getResources().getString(R.string.location_off));
            } else {
                String address = null;
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

                    address = addresses.get(0).getAddressLine(0);
                    Log.d("location", address);
                    tvLocation.setText(address);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnCancel.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

            btnConfirm.setOnClickListener(v1 -> {
                if (ShipperInfo.getInstance().isActive()) {
                    ShipperInfo.getInstance().setActive(false);
                    MySocket.disconnect();
                    btnPower.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    ShipperInfo.getInstance().setActive(true);
                    MySocket.connect();
                    btnPower.setBackgroundColor(getResources().getColor(R.color.green_light));
                }

                alertDialog.dismiss();
            });

            alertDialog.setView(checkInView);
            alertDialog.show();
        });

        return root;
    }
}