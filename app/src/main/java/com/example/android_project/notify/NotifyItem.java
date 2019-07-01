package com.example.android_project.notify;

public class NotifyItem {

    public String notify_content;
    public String notify_title;
    public String notify_date;

    public NotifyItem(String notify_title,String notify_content,String notify_date) {
        this.notify_content = notify_content;
        this.notify_title = notify_title;
        this.notify_date = notify_date;
    }


    public String getNotify_date() {
        return notify_date;
    }

    public void setNotify_date(String notify_date) {
        this.notify_date = notify_date;
    }

    public String getNotify_content() {
        return notify_content;
    }

    public void setNotify_content(String notify_content) {
        this.notify_content = notify_content;
    }

    public String getNotify_title() {
        return notify_title;
    }

    public void setNotify_title(String notify_title) {
        this.notify_title = notify_title;
    }
}
