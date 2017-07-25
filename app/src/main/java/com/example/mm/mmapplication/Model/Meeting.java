package com.example.mm.mmapplication.Model;

import com.example.mm.mmapplication.Model.categories.ActivityCategory;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class Meeting {

    public Long id;

    public String title;

    public ActivityCategory activityCategory;

    public String date;

    public String timeFrom;

    public String timeTo;

    public Set<User> users = new TreeSet<>();

    //public Chat chat;

    public Meeting() {
    }

    @Override
    public String toString() {
        return String.format("%d, %s %s, (%s - %s)", id, title, date, timeFrom, timeTo);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(ActivityCategory activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
