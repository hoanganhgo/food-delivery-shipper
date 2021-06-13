package com.hcmus.fit.shipper.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.OrderPagerAdapter;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.network.MySocket;

public class OrderManagerFragment extends Fragment {

    private ImageButton btnPower;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("order","onCreateView Order fragment");
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        btnPower = root.findViewById(R.id.btn_power);

        viewPager.setAdapter(orderPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        btnPower.setOnClickListener(v -> {
            if (ShipperInfo.getInstance().isActive()) {
                ShipperInfo.getInstance().setActive(false);
                MySocket.disconnect();
                btnPower.setBackgroundColor(getResources().getColor(R.color.green_light));
            } else {
                ShipperInfo.getInstance().setActive(true);
                MySocket.connect();
                btnPower.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        return root;
    }
}