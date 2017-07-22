package com.example.mm.mmapplication.Model;

import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.ActivityTime;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Win8.1 on 18.07.2017.
 */

public class ActivityModel {
    public String title;

    public ActivityCategory activityCategory;

    public ActivityTime activityTime;

    public String date;

    public String timeFrom;

    public String timeTo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {

        this.activityCategory = ActivityCategory.valueOf(activityCategory);

    }

    public ActivityTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {

        switch (activityTime) {
            case "Only once":
                this.activityTime = ActivityTime.ONLY_ONCE;
                break;
            case "Every day":
                this.activityTime = ActivityTime.EVERY_DAY;
                break;
            case "Every week":
                this.activityTime = ActivityTime.EVERY_WEEK;
                break;
            case "Every month":
                this.activityTime = ActivityTime.EVERY_MONTH;
                break;
            default:
                this.activityTime = ActivityTime.ONLY_ONCE;
        }

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
}
