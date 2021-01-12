package com.digitalphotolibrary.demo.service.impl;

import com.digitalphotolibrary.demo.dao.PhotoRepository;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.service.PhotoFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoFilterServiceImpl implements PhotoFilterService {

    @Autowired
    PhotoRepository photoRepository;

    //0: 全放行
    static int filterMode = 0;

    public static int getFilterMode() {
        return filterMode;
    }

    public void save(Photo photo){
        if(PhotoFilterServiceImpl.filterMode == 0){
            photo.setCondition(2);
        }
        photoRepository.save(photo);
    }

    public static void switchMode() {
        if(PhotoFilterServiceImpl.filterMode == 1)
            PhotoFilterServiceImpl.filterMode = 0;
        else
            PhotoFilterServiceImpl.filterMode = 1;
    }

}
