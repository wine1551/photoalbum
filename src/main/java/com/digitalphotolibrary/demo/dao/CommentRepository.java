package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.Comment;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.PhotoLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByPhoto(Photo photo);

}
