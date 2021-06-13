package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.models.ChatManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatBoxAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;

    public ChatBoxAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ChatManager.getInstance().chatBoxListSize();
    }

    @Override
    public Object getItem(int position) {
        return ChatManager.getInstance().getChatBox(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_chat_box, null);
            holder = new MyViewHolder();
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar);
            holder.tvUserName = convertView.findViewById(R.id.tv_user_name);
            holder.tvLastMessage = convertView.findViewById(R.id.tv_last_message);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class MyViewHolder {
        CircleImageView ivAvatar;
        TextView tvUserName;
        TextView tvLastMessage;
    }
}
