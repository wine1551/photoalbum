package com.digitalphotolibrary.demo.entity;

import org.apache.tomcat.jni.File;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "photo")
public class Photo implements Comparable<Photo>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "photoLibrary_id")
    private PhotoLibrary photoLibrary;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "img" ,nullable = false, length=10000000)
    private String photo;

    @Column(name = "up" ,nullable = true, length=30)
    private int likes = 0;

    //0: 未通过 1: 通过 2: 驳回
    @Column(name = "cond", nullable = true, length = 10)
    private int condition = 0;

    @CreatedDate
    public Date createTime;

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getLikes() {
        return likes;
    }

    public int like(){
        likes++;
        return likes;
    }

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

    public PhotoLibrary getPhotoLibrary() {
        return photoLibrary;
    }

    public void setPhotoLibrary(PhotoLibrary photoLibrary) {
        this.photoLibrary = photoLibrary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {

        this.photo = Base64.getEncoder().encodeToString(photo);
    }

    @Override
    public int compareTo(Photo o) {
        return (int) (o.getCreateTime().getTime() - this.getCreateTime().getTime());
    }
}
