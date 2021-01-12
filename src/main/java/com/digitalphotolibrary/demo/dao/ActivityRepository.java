package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.Activity;
import com.digitalphotolibrary.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    public List<Activity> findByCreateTimeAfter(Date time);
    public Activity findByName(String name);
//    public

}
