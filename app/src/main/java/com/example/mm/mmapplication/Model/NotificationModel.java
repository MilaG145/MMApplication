package com.example.mm.mmapplication.Model;

import com.example.mm.mmapplication.Model.categories.NotificationCategory;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class NotificationModel {

    private Long id;

    private User receiver;

    private NotificationCategory category;

    private Meeting meeting;

    private FriendRequest friendRequest;

    private boolean checked;

    public NotificationModel() {

    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public NotificationCategory getCategory() {
        return category;
    }

    public void setCategory(NotificationCategory category) {
        this.category = category;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
