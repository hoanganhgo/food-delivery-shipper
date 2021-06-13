package com.hcmus.fit.shipper.models;

import java.util.ArrayList;

public class ChatManager {
    private static ChatManager instance = null;
    private ArrayList<ChatBox> chatBoxList;

    private ChatManager() {
        this.chatBoxList = new ArrayList<>();
    }

    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();

            ChatBox chatBox = new ChatBox();
            instance.addChatBox(chatBox);
            instance.addChatBox(chatBox);
            instance.addChatBox(chatBox);
            instance.addChatBox(chatBox);
        }

        return instance;
    }

    public int chatBoxListSize() {
        return this.chatBoxList.size();
    }

    public ChatBox getChatBox(int index) {
        return this.chatBoxList.get(index);
    }

    public void addChatBox(ChatBox chatBox) {
        this.chatBoxList.add(chatBox);
    }

}
