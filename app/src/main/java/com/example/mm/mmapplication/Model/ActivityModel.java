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

    public Date date;

    public Time timeFrom;

    public Time timeTo;
}
