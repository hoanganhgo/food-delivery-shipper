package com.hcmus.fit.shipper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.activities.OrderActivity;
import com.hcmus.fit.shipper.adapters.OrderAdapter;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderFragment extends Fragment {
    private int title;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderList;
    private ListView lvOrders;

    public OrderFragment(int title) {
        this.title = title;

        switch (title) {
            case R.string.process:
                this.orderList = OrderManager.getInstance().getProcessList();
                break;

            case R.string.complete:
                this.orderList = OrderManager.getInstance().getCompleteList();
                break;
        }
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
        View root = inflater.inflate(R.layout.fragment_order_main, container, false);
        lvOrders = root.findViewById(R.id.lv_orders);

        this.orderAdapter = new OrderAdapter(getContext(), orderList);
        lvOrders.setAdapter(this.orderAdapter);
        setupAdapter();

        lvOrders.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this.getActivity(), OrderActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("title", this.title);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.orderAdapter.notifyDataSetChanged();
    }

    public int getTitle() {
        return title;
    }

    private void setupAdapter() {
        switch (title) {
            case R.string.process:
                OrderManager.getInstance().setProcessAdapter(this.orderAdapter);
                break;

            case R.string.complete:
                OrderManager.getInstance().setCompleteAdapter(this.orderAdapter);
                break;
        }
    }
}