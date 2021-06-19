package com.hcmus.fit.shipper.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.hcmus.fit.shipper.activities.ChatActivity;
import com.hcmus.fit.shipper.network.MySocket;

import java.net.URL;
import java.util.ArrayList;

public class ChatBox {
    private String roomId;
    private Bitmap avatar;
    private String userId;
    private String userName;
    private String lastMessage;
    private final ArrayList<ChatModel> chatList = new ArrayList<>();
    private ChatActivity activity;

    public ChatBox() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatarUrl) {
        try {
            URL url = new URL(avatarUrl);
            this.avatar = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void addMessage(ChatModel chatModel) {
        this.chatList.add(chatModel);

        if (chatModel.isMyself()) {
            MySocket.sendMessage(this.roomId, chatModel.getContent());
        } else {
            if (activityActive()) {
                this.activity.runOnUiThread(() -> activity.updateChatBox());
            }
        }
    }

    public int getChatListSize() {
        return this.chatList.size();
    }

    public ChatModel getChatIndex(int index) {
        return this.chatList.get(index);
    }

    public void setActivity(ChatActivity activity) {
        this.activity = activity;
    }

    private boolean activityActive() {
        return this.activity != null && !this.activity.isDestroyed();
    }
}
