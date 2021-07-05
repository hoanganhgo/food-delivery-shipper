package com.hcmus.fit.shipper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.adapters.MessageAdapter;
import com.hcmus.fit.shipper.models.ChatBox;
import com.hcmus.fit.shipper.models.ChatManager;
import com.hcmus.fit.shipper.models.ChatModel;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatActivity extends AppCompatActivity {

    private ListView lvMessage;
    private MessageAdapter messageAdapter;
    private ChatBox chatBox;
    private CircleImageView ivAvatar;
    private TextView tvUserName;
    private EditText edtMessage;
    private ImageButton btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lvMessage = findViewById(R.id.lv_message);
        ivAvatar = findViewById(R.id.iv_title_avatar);
        tvUserName = findViewById(R.id.tv_user_name);
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send_message);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        String customerId = null;
        if (position < 0) {
            customerId = intent.getStringExtra("customerId");
            this.chatBox = ChatManager.getInstance().getChatBoxByCustomer(customerId);
        } else {
            this.chatBox = ChatManager.getInstance().getChatBox(position);
        }

        if (this.chatBox == null) {
            onBackPressed();
        }

        this.chatBox.setActivity(this);
        this.ivAvatar.setImageBitmap(this.chatBox.getAvatar());
        this.tvUserName.setText(this.chatBox.getUserName());

        messageAdapter = new MessageAdapter(this, this.chatBox);
        lvMessage.setAdapter(messageAdapter);

        btnSend.setOnClickListener(v -> {
            ChatModel chatModel = new ChatModel();
            chatModel.setMyself(true);
            chatModel.setContent(edtMessage.getText().toString());
            this.chatBox.addMessage(chatModel);
            messageAdapter.notifyDataSetChanged();

            edtMessage.setText("");
        });
    }

    public void updateChatBox() {
        this.messageAdapter.notifyDataSetChanged();
    }
}
