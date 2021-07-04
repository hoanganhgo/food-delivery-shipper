package com.hcmus.fit.shipper.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.network.OrderNetwork;
import com.hcmus.fit.shipper.ui.IncomeFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MyViewHolder> {
    private IncomeFragment fragment;
    private List<Integer> monthList;
    private HashMap<Integer, MyViewHolder> holderMap = new HashMap<>();
    public boolean[] selected = new boolean[12];

    public MonthAdapter(IncomeFragment fragment, List<Integer> monthList) {
        this.fragment = fragment;
        this.monthList = monthList;
        Arrays.fill(this.selected, false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_month, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int month = monthList.get(position);
        holder.tvMonth.setText(month);
        holder.tvMonth.setBackgroundResource(selected[position] ? R.color.grey_bg : R.color.white);

        holder.btnMonth.setOnClickListener(v -> {
            setAllNotSelected();
            selected[position] = true;
            holder.tvMonth.setBackgroundResource(R.color.grey_bg);
            OrderNetwork.getIncome(fragment, position + 1);
        });

        holderMap.put(position, holder);
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnMonth;
        TextView tvMonth;
        MyViewHolder(View view) {
            super(view);
            btnMonth = view.findViewById(R.id.btn_month);
            tvMonth = view.findViewById(R.id.tv_month);
        }
    }

    private void setAllNotSelected() {
        Arrays.fill(selected, false);

        for (MyViewHolder holder : holderMap.values()) {
            holder.tvMonth.setBackgroundResource(R.color.white);
        }
    }
}
