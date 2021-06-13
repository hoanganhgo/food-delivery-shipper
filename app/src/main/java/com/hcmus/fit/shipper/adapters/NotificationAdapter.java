package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.models.NotifyManager;

public class NotificationAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;

    public NotificationAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return NotifyManager.getInstance().getNotifyListSize();
    }

    @Override
    public Object getItem(int position) {
        return NotifyManager.getInstance().getNotify(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_notification, null);
            holder = new MyViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.tvContent = convertView.findViewById(R.id.tv_content);
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_notification);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class MyViewHolder {
        TextView tvTitle;
        TextView tvContent;
        ImageView ivAvatar;
    }
}
