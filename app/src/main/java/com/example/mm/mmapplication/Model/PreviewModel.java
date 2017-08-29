package com.example.mm.mmapplication.Model;

import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.PreviewCategory;

/**
 * Created by Win8.1 on 28.08.2017.
 */

public class PreviewModel {

    private String id;
    private PreviewCategory previewCategory;
    private String title;
    private ActivityCategory activityCategory;
    private String date;
    private String time;

    public PreviewModel() {
    }

    public PreviewModel(String id, PreviewCategory previewCategory, String title, ActivityCategory activityCategory, String date, String time) {
        this.id = id;
        this.previewCategory = previewCategory;
        this.title = title;
        this.activityCategory = activityCategory;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PreviewCategory getPreviewCategory() {
        return previewCategory;
    }

    public void setPreviewCategory(PreviewCategory previewCategory) {
        this.previewCategory = previewCategory;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
