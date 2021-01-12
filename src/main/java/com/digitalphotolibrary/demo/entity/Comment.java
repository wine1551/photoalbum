package com.digitalphotolibrary.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "myComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "text", nullable = false, length = 200)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

}
