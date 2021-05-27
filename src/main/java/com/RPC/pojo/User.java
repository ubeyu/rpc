package com.RPC.pojo;

import java.io.Serializable;

public class User implements Serializable {

    // 客户端和服务端共有的
    private Integer id;
    private String userName;
    private Boolean sex;

    public User() {
    }

    public User(Integer id, String userName, Boolean sex) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                '}';
    }
}
