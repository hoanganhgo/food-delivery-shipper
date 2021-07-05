package com.hcmus.fit.shipper.models;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hcmus.fit.shipper.MainActivity;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.activities.OrderActivity;
import com.hcmus.fit.shipper.network.MySocket;
import com.hcmus.fit.shipper.util.AppUtil;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderManager {
    private static OrderManager instance = null;

    private OrderModel newOrder = null;
    private final ArrayList<OrderModel> processList = new ArrayList<>();
    private final ArrayList<OrderModel> completeList = new ArrayList<>();

    private MainActivity activity;
    private Dialog orderDialog;
    private BaseAdapter processAdapter;
    private BaseAdapter completeAdapter;

    private OrderManager() {

    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }

        return instance;
    }

    public ArrayList<OrderModel> getProcessList() {
        return processList;
    }

    public ArrayList<OrderModel> getCompleteList() {
        return completeList;
    }

    public void setProcessAdapter(BaseAdapter processAdapter) {
        this.processAdapter = processAdapter;
    }

    public void setCompleteAdapter(BaseAdapter completeAdapter) {
        this.completeAdapter = completeAdapter;
    }

    public OrderModel getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(OrderModel newOrder) {
        this.newOrder = newOrder;
    }

    public void setOrderDialog(Dialog orderDialog) {
        this.orderDialog = orderDialog;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void showNewOrder(int timeOut) {
        if (orderDialog != null && newOrder != null && this.activity != null && !this.activity.isDestroyed()) {
            ImageButton btnCancel = orderDialog.findViewById(R.id.btn_cancel);
            TextView tvDistance = orderDialog.findViewById(R.id.tv_distance);
            TextView tvPrice = orderDialog.findViewById(R.id.tv_price);
            TextView tvMerchant = orderDialog.findViewById(R.id.tv_merchant);
            TextView tvPayMerchant = orderDialog.findViewById(R.id.tv_pay_merchant);
            TextView tvCustomer = orderDialog.findViewById(R.id.tv_customer);
            TextView tvReceiveCustomer = orderDialog.findViewById(R.id.tv_receive_customer);
            TextView tvCountDown = orderDialog.findViewById(R.id.tv_count_down);
            ImageButton btnDrag = orderDialog.findViewById(R.id.btn_drag);
            RelativeLayout mainLayout = orderDialog.findViewById(R.id.layout_drag);

            tvDistance.setText(newOrder.getDistance() + " Km");
            tvPrice.setText(AppUtil.convertCurrency(newOrder.getTotal()));

            int subTotal = newOrder.getSubTotal();
            int total = newOrder.getTotal();
            if (newOrder.getPayment() != 0) {
                if (newOrder.hasMerchantTool()) {
                    subTotal = 0;
                }

                total = 0;
            }

            tvMerchant.setText(orderDialog.getContext().getResources().getString(R.string.get_order) + newOrder.getMerchant());
            tvPayMerchant.setText(orderDialog.getContext().getResources().getString(R.string.pay_money)
                    + AppUtil.convertCurrency(subTotal));
            tvCustomer.setText(orderDialog.getContext().getResources().getString(R.string.ship_order) + newOrder.getCustomer());
            tvReceiveCustomer.setText(orderDialog.getContext().getResources().getString(R.string.receive_money) + AppUtil.convertCurrency(total));

            btnCancel.setOnClickListener(v2 -> {
                MySocket.skipOrder(newOrder.getOrderId());
                orderDialog.dismiss();
            });

            ScheduledExecutorService countDownService = Executors.newSingleThreadScheduledExecutor();
            AtomicInteger countDown = new AtomicInteger(timeOut / 1000);
            countDownService.scheduleAtFixedRate(() -> {
                this.activity.runOnUiThread(() -> tvCountDown.setText(String.valueOf(countDown.get())));
                countDown.getAndDecrement();

                if (countDown.get() <= 0) {
                    orderDialog.dismiss();
                    countDownService.shutdown();
                }
            },0, 1, TimeUnit.SECONDS);

            //Drag Button
            btnDrag.setOnTouchListener((view, event) -> {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();

                final int x = (int) event.getRawX();

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    layoutParams.leftMargin = x;
                    layoutParams.rightMargin = 0;
                    layoutParams.bottomMargin = 0;
                    view.setLayoutParams(layoutParams);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("X", String.valueOf(x));
                    int maxWidth = mainLayout.getWidth() - mainLayout.getWidth() / 10;

                    if (x > maxWidth) {
                        OrderManager.getInstance().getProcessList().add(0, newOrder);
                        MySocket.confirmOrder(newOrder.getOrderId());

                        newOrder.setStatus(2);
                        notifyProcessAdapter();
                        orderDialog.dismiss();
                        countDownService.shutdown();

                        Intent intent = new Intent(this.activity, OrderActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("title", R.string.process);
                        this.activity.startActivity(intent);
                    }

                    layoutParams.leftMargin = 0;
                    view.setLayoutParams(layoutParams);
                }

                mainLayout.invalidate();

                return true;
            });

            this.activity.runOnUiThread(() -> orderDialog.show());
        }
    }

    public void notifyProcessAdapter() {
        if (processAdapter != null && this.activity != null && !this.activity.isDestroyed()) {
            this.activity.runOnUiThread(() -> processAdapter.notifyDataSetChanged());
        }
    }

    public void notifyCompleteAdapter() {
        if (completeAdapter != null  && this.activity != null && !this.activity.isDestroyed()) {
            this.activity.runOnUiThread(() -> completeAdapter.notifyDataSetChanged());
        }
    }
}
