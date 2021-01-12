package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.PhotoLibrary;
import com.digitalphotolibrary.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoLibraryRepository extends JpaRepository<PhotoLibrary, Integer> {

    public List<PhotoLibrary> findByUser(User user);
    public PhotoLibrary findByUserAndName(User user, String name);

}
