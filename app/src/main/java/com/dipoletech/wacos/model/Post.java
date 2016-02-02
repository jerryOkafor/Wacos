package com.dipoletech.wacos.model;/**
 * Created by DABBY(3pleMinds) on 27-Jan-16.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * DABBY(3pleMinds) 27-Jan-16 3:35 AM 2016 01
 * 27 03 35 Wacos
 **/
public class Post  {
    private long date;
    private String what, displayName;
    private String uid;
    private int views;

    private List<String> attachments;
    public Post()
    {
        attachments = new ArrayList<>();

    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhat() {
        return what;
    }


    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

