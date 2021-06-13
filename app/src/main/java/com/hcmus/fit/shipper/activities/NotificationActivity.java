package com.hcmus.fit.shipper.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.NotificationAdapter;


public class NotificationActivity extends AppCompatActivity {

    private ListView lvNotify;
    private NotificationAdapter notifyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        lvNotify = findViewById(R.id.lv_notify);
        notifyAdapter = new NotificationAdapter(this);
        lvNotify.setAdapter(notifyAdapter);
    }
}
