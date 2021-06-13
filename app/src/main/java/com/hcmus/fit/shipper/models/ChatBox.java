package com.hcmus.fit.shipper.models;

import java.util.ArrayList;

public class ChatBox {
    private String id;
    private String avatarUrl;
    private String userName;
    private String lastMessage;
    private ArrayList<ChatModel> chatList = new ArrayList<>();

    public ChatBox() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
