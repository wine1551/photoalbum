package com.digitalphotolibrary.demo.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "myActivity", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Activity implements Comparable<Activity>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @CreatedDate
    private Date createTime;

    @OneToMany()
    @JoinColumn(name = "photo_id")
    private List<Photo> photos;

    @Column(name = "name" ,nullable = false, length=30)
    private String name;


    @Column(name = "text" ,nullable = false, length=10000)
    private String text;

    public String getName() {
        return name;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Activity o) {
        return (int) (o.getCreateTime().getTime() - this.getCreateTime().getTime());
    }
}
