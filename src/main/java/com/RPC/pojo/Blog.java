package com.RPC.pojo;

import java.io.Serializable;

// pojo类
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor

public class Blog implements Serializable {
    private int id;
    private Integer userId;
    private String title;

    public Blog() {
    }

    public Blog(int id, Integer userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                '}';
    }
}
