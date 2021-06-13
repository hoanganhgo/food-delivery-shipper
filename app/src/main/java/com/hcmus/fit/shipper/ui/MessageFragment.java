package com.hcmus.fit.shipper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.activities.ChatActivity;
import com.hcmus.fit.shipper.adapters.ChatBoxAdapter;

public class MessageFragment extends Fragment {

    private ListView lvChatBox;
    private ChatBoxAdapter chatAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        lvChatBox = root.findViewById(R.id.lv_chat_box);

        chatAdapter = new ChatBoxAdapter(getContext());
        lvChatBox.setAdapter(chatAdapter);

        lvChatBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}