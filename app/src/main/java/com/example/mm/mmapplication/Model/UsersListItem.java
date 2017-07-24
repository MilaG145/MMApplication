package com.example.mm.mmapplication.Model;

import java.io.Serializable;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class UsersListItem implements Serializable {

    private Long userId;
    private String fullName;
    private Boolean friend;
    private String email;

    public UsersListItem(Long userId, String fullName, Boolean friend, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.friend = friend;
        this.email = email;

    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getFriend() {
        return friend;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return userId+" "+fullName+" "+email;
    }
}
