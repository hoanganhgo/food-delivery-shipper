package com.hcmus.fit.shipper.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.OrderAdapter;
import com.hcmus.fit.shipper.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderHolderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;

    private OrderAdapter orderAdapter;

    private final List<OrderModel> orderList = new ArrayList<>();

    public static OrderHolderFragment newInstance(int index) {
        OrderHolderFragment fragment = new OrderHolderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("order", "onCreate Place Holder");
        //pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_main, container, false);
        final ListView lvOrders = root.findViewById(R.id.lv_orders);

        this.orderAdapter = new OrderAdapter(getContext(), orderList);
        genOrders();
        lvOrders.setAdapter(this.orderAdapter);
        return root;
    }

    void genOrders() {
        orderList.add(new OrderModel());
        orderList.add(new OrderModel());
        orderList.add(new OrderModel());
        orderList.add(new OrderModel());
    }
}