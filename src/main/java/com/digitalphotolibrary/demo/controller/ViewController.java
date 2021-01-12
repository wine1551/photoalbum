package com.digitalphotolibrary.demo.controller;


import com.digitalphotolibrary.demo.dao.ActivityRepository;
import com.digitalphotolibrary.demo.dao.PhotoRepository;
import com.digitalphotolibrary.demo.entity.Activity;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/photoview")
public class ViewController {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping()
    public String mainView(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(calendar.DATE,-5);
        now=calendar.getTime();

        List<Photo> photos = photoRepository.findByCreateTimeAfterAndCondition(now,2);
        List<Activity> activitys = activityRepository.findByCreateTimeAfter(now);
        for (int i = 0 ; i < photos.size() ; i++){
            Photo photo = photos.get(i);
            if(photo.getPhotoLibrary().getPrivate()==true&&photo.getUser().getId()!=user.getId()){
                photos.remove(i);
            }
        }
        Collections.sort(activitys);
        Collections.sort(photos);
        model.addAttribute("activities",activitys);
        model.addAttribute("photos",photos);
        return "photoview";
    }

    @GetMapping(value = "/activity")
    public String activityDetail(HttpSession session, Model model,
                                 @RequestParam(name = "activityId",required = false, defaultValue = "-1") int activityId) {

        System.out.println(activityId);
        Optional<Activity> opactivity = activityRepository.findById(activityId);
        if(opactivity.isPresent()==false) {
            model.addAttribute("error", "没有此活动");
        }
        Activity activity = opactivity.get();
        model.addAttribute("activity", activity);
        return "activitydetail";
    }

}
