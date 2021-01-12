package com.digitalphotolibrary.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "myUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "sex", nullable = false, length = 2)
    private String sex;

    @Column(name = "password", nullable = false, length = 2)
    private String password;

    @Column(name = "is_auth", nullable = true, length = 1)
    private Boolean is_auth = false;

    @Column(name = "is_shut", nullable = true, length = 1)
    private Boolean is_shut = false;

    public Boolean getIs_shut() {
        return is_shut;
    }

    public void setIs_shut(Boolean is_shut) {
        this.is_shut = is_shut;
    }

    public Boolean getIs_auth() {
        if(is_auth==null)is_auth=false;
        return is_auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}