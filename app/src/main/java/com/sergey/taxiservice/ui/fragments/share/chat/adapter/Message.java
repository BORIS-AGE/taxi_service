package com.sergey.taxiservice.ui.fragments.share.chat.adapter;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {

    private String uuid;
    private String message;
    private Date date;
    private User user;

    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String uuid) {
        this.uuid = uuid;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
