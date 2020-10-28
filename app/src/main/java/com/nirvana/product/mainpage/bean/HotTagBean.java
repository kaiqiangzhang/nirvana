package com.nirvana.product.mainpage.bean;


import androidx.annotation.NonNull;

import java.lang.annotation.ElementType;
import java.util.Comparator;
import java.util.Objects;

/**
 * 热门标签javaBean对象
 * Created by kriszhang on 2017/8/7.
 */

public class HotTagBean implements Comparable<HotTagBean>{
    private int id;
    private String name;
    private int lastPostId;
    private int posts;
    private int featured;
    private int hots;
    private int locked;
    private Object post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastPostId() {
        return lastPostId;
    }

    public void setLastPostId(int lastPostId) {
        this.lastPostId = lastPostId;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getHots() {
        return hots;
    }

    public void setHots(int hots) {
        this.hots = hots;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public Object getPost() {
        return post;
    }

    public void setPost(Object post) {
        this.post = post;
    }


    @Override
    public int compareTo(@NonNull HotTagBean bean) {
        int result = this.hots- bean.getHots();
        if (result !=0){
            return result;
        }else {
            return name.compareTo(bean.getName());
        }
    }
}
