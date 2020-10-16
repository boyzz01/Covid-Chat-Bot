package com.example.covidchatbot.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChatModel extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String message;
    private String sender;
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
