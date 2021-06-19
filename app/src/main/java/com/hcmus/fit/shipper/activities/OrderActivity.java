package com.hcmus.fit.shipper.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.DetailPagerAdapter;
import com.hcmus.fit.shipper.models.ChatManager;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.network.MySocket;

public class OrderActivity extends AppCompatActivity {

    private int title;
    private OrderModel orderModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        this.title = intent.getIntExtra("title",0);

        if (this.title == R.string.process) {
            this.orderModel = OrderManager.getInstance().getProcessList().get(position);
        } else if (this.title == R.string.complete) {
            this.orderModel = OrderManager.getInstance().getCompleteList().get(position);
        }

        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(this,
                getSupportFragmentManager(), orderModel);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //Drag Button
        ImageButton btnDrag = findViewById(R.id.btn_drag);
        TextView tvOrderStatus = findViewById(R.id.tv_order_status);
        RelativeLayout mainLayout = findViewById(R.id.layout_drag);

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

                if (x > maxWidth && orderModel.getStatus() == 2) {
                    tvOrderStatus.setText(R.string.delivered);
                    mainLayout.setBackgroundColor(getResources().getColor(R.color.orange));
                    orderModel.setStatus(3);
                    MySocket.tookFood(orderModel.getOrderId());
                } else if (x > maxWidth && orderModel.getStatus() == 3) {
                    OrderManager.getInstance().getCompleteList().add(0, orderModel);
                    OrderManager.getInstance().getProcessList().remove(orderModel);
                    OrderManager.getInstance().notifyProcessAdapter();
                    OrderManager.getInstance().notifyCompleteAdapter();
                    orderModel.setStatus(4);
                    ChatManager.getInstance().removeChatBox(position);
                    MySocket.delivered(orderModel.getOrderId());
                    onBackPressed();
                }

                layoutParams.leftMargin = 0;
                view.setLayoutParams(layoutParams);
            }

            mainLayout.invalidate();

            return true;
        });

        if (this.title == R.string.complete) {
            mainLayout.setVisibility(View.GONE);
        } else {
            mainLayout.setVisibility(View.VISIBLE);
        }

        if (orderModel.getStatus() == 3) {
            tvOrderStatus.setText(R.string.delivered);
            mainLayout.setBackgroundColor(getResources().getColor(R.color.orange));
        }
    }
}
