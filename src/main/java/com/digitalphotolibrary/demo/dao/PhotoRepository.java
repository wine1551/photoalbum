package com.digitalphotolibrary.demo.dao;

import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.PhotoLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    public List<Photo> findByPhotoLibrary(PhotoLibrary pl);
    public List<Photo> findByPhotoLibraryAndCondition(PhotoLibrary pl,int cond);
    public List<Photo> findByCreateTimeAfter(Date date);
    public List<Photo> findByCreateTimeAfterAndCondition(Date date, int cond);
    public List<Photo> findByCondition(int cond);
}
