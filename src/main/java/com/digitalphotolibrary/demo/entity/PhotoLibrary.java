package com.digitalphotolibrary.demo.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Entity
@Table(name = "PhotoLibrary" )
public class PhotoLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_private", nullable = true, length = 1)
    private Boolean isPrivate = false;

    @Column(name = "desctription", nullable = true, length = 200)
    private String desctription = "未描述";

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getDesctription() {
        return desctription;
    }

    public void setDesctription(String desctription) {
        this.desctription = desctription;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
