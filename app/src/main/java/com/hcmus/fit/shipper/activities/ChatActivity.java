package com.hcmus.fit.shipper.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.MessageAdapter;
import com.hcmus.fit.shipper.adapters.NotificationAdapter;
import com.hcmus.fit.shipper.models.ChatBox;
import com.hcmus.fit.shipper.models.ChatModel;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    private ListView lvMessage;
    private MessageAdapter messageAdapter;
    private ChatBox chatBox;
    private EditText edtMessage;
    private ImageButton btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lvMessage = findViewById(R.id.lv_message);
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send_message);

        ArrayList<ChatModel> list = new ArrayList<>();
        list.add(new ChatModel());
        list.add(new ChatModel());
        list.add(new ChatModel());
        list.add(new ChatModel());

        messageAdapter = new MessageAdapter(this, list);
        lvMessage.setAdapter(messageAdapter);

        btnSend.setOnClickListener(v -> {
            list.add(new ChatModel());
            messageAdapter.notifyDataSetChanged();
        });
    }
}
