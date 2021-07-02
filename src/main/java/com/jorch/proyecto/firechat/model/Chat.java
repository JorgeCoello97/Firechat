package com.jorch.proyecto.firechat.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JORCH on 18/03/2017.
 */

public class Chat {
    private HashMap<String,Message> messages;
    private HashMap<String,Boolean> users;

    public Chat() {

    }

    public Chat(HashMap<String, Message> messages, HashMap<String, Boolean> users) {
        this.messages = messages;
        this.users = users;
    }

    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    public HashMap<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, Boolean> users) {
        this.users = users;
    }
}
