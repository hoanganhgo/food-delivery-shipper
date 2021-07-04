package com.hcmus.fit.shipper.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.MonthAdapter;
import com.hcmus.fit.shipper.network.OrderNetwork;
import com.hcmus.fit.shipper.util.AppUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class IncomeFragment extends Fragment {
    private RecyclerView lvMonth;
    private MonthAdapter monthAdapter;
    private TextView tvTotal;
    private TextView tvTotalIncome;
    private TextView tvTotalOrders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_income, container, false);
        lvMonth = root.findViewById(R.id.lv_month);
        tvTotal = root.findViewById(R.id.tv_total_value);
        tvTotalIncome = root.findViewById(R.id.tv_total_income);
        tvTotalOrders = root.findViewById(R.id.tv_total_orders);

        List<Integer> months = Arrays.asList(R.string.month_01, R.string.month_02, R.string.month_03,
                R.string.month_04, R.string.month_05, R.string.month_06, R.string.month_07, R.string.month_08,
                R.string.month_09, R.string.month_10, R.string.month_11, R.string.month_12);
        monthAdapter = new MonthAdapter(this, months);

        LinearLayoutManager bnLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        bnLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvMonth.setLayoutManager(bnLayoutManager);
        lvMonth.setItemAnimator(new DefaultItemAnimator());
        lvMonth.setAdapter(monthAdapter);

        int month = Calendar.getInstance().get(Calendar.MONTH);
        lvMonth.scrollToPosition(month);
        monthAdapter.selected[month] = true;
        Log.d("month", String.valueOf(month));
        OrderNetwork.getIncome(this, month + 1);
        return root;
    }

    public void updateIncome(int totalValue, int totalIncome, int totalOrders) {
        tvTotal.setText(AppUtil.convertCurrency(totalValue));
        tvTotalIncome.setText(AppUtil.convertCurrency(totalIncome));
        tvTotalOrders.setText(totalOrders + " " + getResources().getString(R.string.orders));
    }
}