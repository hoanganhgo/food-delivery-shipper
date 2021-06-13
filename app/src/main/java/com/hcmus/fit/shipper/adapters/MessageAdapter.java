package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.models.ChatModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;
    private ArrayList<ChatModel> chatList;

    public MessageAdapter(Context context, ArrayList<ChatModel> chatList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.chatList = chatList;
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_message, null);
            holder = new MyViewHolder();
            holder.ivUserAvatar = convertView.findViewById(R.id.iv_user_avatar);
            holder.tvUserMessage = convertView.findViewById(R.id.tv_user_message);
            holder.tvMyMessage = convertView.findViewById(R.id.tv_my_message);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class MyViewHolder {
        CircleImageView ivUserAvatar;
        TextView tvUserMessage;
        TextView tvMyMessage;
    }
}
