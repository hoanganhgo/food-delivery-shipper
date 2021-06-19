package com.hcmus.fit.shipper.models;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ChatManager {
    private static ChatManager instance = null;
    private final ArrayList<ChatBox> chatBoxList;
    private Fragment fragment;

    private ChatManager() {
        this.chatBoxList = new ArrayList<>();
    }

    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }

        return instance;
    }

    public int chatBoxListSize() {
        return this.chatBoxList.size();
    }

    public ChatBox getChatBox(int index) {
        return this.chatBoxList.get(index);
    }

    public ChatBox getChatBoxByCustomer(String customerId) {
        for (ChatBox chatBox : this.chatBoxList) {
            if (chatBox.getUserId().equals(customerId)) {
                return chatBox;
            }
        }

        return null;
    }

    public void addChatBox(ChatBox chatBox) {
        this.chatBoxList.add(chatBox);
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ChatBox getChatBox(String roomId) {
        for (ChatBox chatBox : this.chatBoxList) {
            if (chatBox.getRoomId().equals(roomId)) {
                return chatBox;
            }
        }

        return null;
    }

    public void removeChatBox(int index) {
        this.chatBoxList.remove(index);
    }

}
