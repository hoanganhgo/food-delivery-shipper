package com.hcmus.fit.shipper.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.util.AppUtil;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final List<OrderModel> orderModelList;

    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.orderModelList = orderModelList;
    }

    @Override
    public int getCount() {
        return orderModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_order, null);
            holder = new MyViewHolder();
            holder.tvOrderCode = convertView.findViewById(R.id.tv_order_code);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.tvPoint = convertView.findViewById(R.id.tv_point);
            holder.tvMerchant = convertView.findViewById(R.id.tv_merchant);
            holder.tvPayMerchant = convertView.findViewById(R.id.tv_pay_merchant);
            holder.tvTimeGetOrder = convertView.findViewById(R.id.tv_time_get_order);
            holder.tvCustomer = convertView.findViewById(R.id.tv_customer);
            holder.tvReceiveCustomer = convertView.findViewById(R.id.tv_receive_customer);
            holder.tvTimeShip = convertView.findViewById(R.id.tv_time_ship);
            holder.tvDistance = convertView.findViewById(R.id.tv_distance);
            holder.tvCompleteAt = convertView.findViewById(R.id.tv_order_time);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        OrderModel orderModel = orderModelList.get(position);

        holder.tvOrderCode.setText("#" + orderModel.getOrderId());
        holder.tvPrice.setText(AppUtil.convertCurrency(orderModel.getTotal()));

        int subTotal = orderModel.getSubTotal();
        int total = orderModel.getTotal();
        if (orderModel.getPayment() != 0) {
            if (orderModel.hasMerchantTool()) {
                subTotal = 0;
            }

            total = 0;
        }

        holder.tvPoint.setText(orderModel.getPoint() + convertView.getResources().getString(R.string.point));
        holder.tvMerchant.setText(convertView.getResources().getString(R.string.get_order)
                + orderModel.getMerchant());
        holder.tvPayMerchant.setText(convertView.getResources().getString(R.string.pay_money)
                + AppUtil.roundCurrency(subTotal));
        holder.tvTimeGetOrder.setText(convertView.getResources().getString(R.string.get_now));
        holder.tvCustomer.setText(convertView.getResources().getString(R.string.ship_order)
                + orderModel.getCustomer());
        holder.tvReceiveCustomer.setText(convertView.getResources().getString(R.string.receive_money)
                + AppUtil.roundCurrency(total));
        holder.tvTimeShip.setText(convertView.getResources().getString(R.string.ship_now));
        holder.tvDistance.setText(orderModel.getDistance() + " km");
        holder.tvCompleteAt.setText(convertView.getResources().getString(R.string.schedule_ship)
                + orderModel.getCompleteAt());

        return convertView;
    }

    static class MyViewHolder {
        TextView tvOrderCode;
        TextView tvPrice;
        TextView tvPoint;

        TextView tvMerchant;
        TextView tvPayMerchant;
        TextView tvTimeGetOrder;

        TextView tvCustomer;
        TextView tvReceiveCustomer;
        TextView tvTimeShip;
        TextView tvDistance;

        TextView tvCompleteAt;
    }
}
