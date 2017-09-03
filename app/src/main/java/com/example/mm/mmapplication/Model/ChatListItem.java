package com.example.mm.mmapplication.Model;

import java.io.Serializable;

/**
 * Created by Win8.1 on 03.09.2017.
 */

public class ChatListItem implements Serializable {
    String name;
    String id;

    public ChatListItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
