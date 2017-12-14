package com.nirvana.code.model;

/**
 * Created by kriszhang on 2016/12/28.
 */

public class Channel {
    private int id;
    private String url;
    private String name;
    private int iconResourceId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
