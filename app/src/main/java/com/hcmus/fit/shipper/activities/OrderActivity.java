package com.hcmus.fit.shipper.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.DetailPagerAdapter;

public class OrderActivity extends AppCompatActivity {

    private int xDelta;
    private int yDelta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //Drag Button
        ImageButton btnDrag = findViewById(R.id.btn_drag);
        RelativeLayout mainLayout = findViewById(R.id.layout_drag);

        btnDrag.setOnTouchListener((view, event) -> {
            final int x = (int) event.getRawX();
            //final int y = (int) event.getRawY();

            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = x - xDelta;
                //layoutParams.topMargin = y - yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                view.setLayoutParams(layoutParams);
            }
            mainLayout.invalidate();
            return true;
        });
    }
}
