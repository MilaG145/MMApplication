package com.example.mm.mmapplication.Model;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class FriendRequest {

    private Long id;

    private User sender;

    private User receiver;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return receiver;
    }

    public void setReciever(User reciever) {
        this.receiver = reciever;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
