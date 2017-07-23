package com.example.mm.mmapplication.Model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class UsersListItem implements Serializable{

    private Long userId;
    private String fullName;
    private Boolean friend;

    public UsersListItem(Long userId, String fullName, Boolean friend) {
        this.userId = userId;
        this.fullName = fullName;
        this.friend= friend;
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
}
