package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.Message;
import com.digitalphotolibrary.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findByCreateTimeAfterAndUser(Date date, User user);
    public List<Message> findByUser(User user);

}
