package com.sergey.taxiservice.models.chat;

public class MessageApiModel {

    private String id;
    private String message;
    private String date;
    private String userName;
    private String avatar;
    private int userId;
    private int chatId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public MessageDbModel toDbModel() {
        MessageDbModel dbModel = new MessageDbModel();
        dbModel.setId(getId());
        dbModel.setAvatar(getAvatar());
        dbModel.setChatId(getChatId());
        dbModel.setDate(getDate());
        dbModel.setMessage(getMessage());
        dbModel.setUserId(getUserId());
        dbModel.setUserName(getUserName());

        return dbModel;
    }
}
