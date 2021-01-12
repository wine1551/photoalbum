package com.digitalphotolibrary.demo.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Message implements Comparable<Message>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @CreatedDate
    private Date createTime;

    @Column(name = "text", nullable = false, length = 1000)
    private String text;

    @Column(name = "img", nullable = true, length = 100000)
    private String photo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int compareTo(Message o) {
        return (int) (o.getCreateTime().getTime() - this.getCreateTime().getTime());
    }
}
