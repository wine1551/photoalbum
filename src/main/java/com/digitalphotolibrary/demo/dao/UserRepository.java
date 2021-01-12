package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByName(String name);

}
