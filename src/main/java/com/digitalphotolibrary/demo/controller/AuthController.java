package com.digitalphotolibrary.demo.controller;


import com.digitalphotolibrary.demo.dao.*;
import com.digitalphotolibrary.demo.entity.Activity;
import com.digitalphotolibrary.demo.entity.Message;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.User;
import com.digitalphotolibrary.demo.service.impl.PhotoFilterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PhotoLibraryRepository photoLibraryRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    MessageRepository messageRepository;


    @GetMapping()
    public String authMainPage(Model model){
//        Date now = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(now);
//        calendar.add(calendar.DATE,-5);
//        now=calendar.getTime();
        PhotoFilterServiceImpl photoFilterServiceImpl = new PhotoFilterServiceImpl();
        model.addAttribute("nowFilterCondition", PhotoFilterServiceImpl.getFilterMode());
        model.addAttribute("activities",activityRepository.findAll());
        return "authmainpage";
    }

    @GetMapping(value = "switchCondition")
    public String switchCondition(){
        PhotoFilterServiceImpl.switchMode();
        return "redirect:/auth";
    }

    @GetMapping(value = "filterphoto")
    public String filterPhoto(Model model){
        model.addAttribute("photos",photoRepository.findByCondition(0));
        return "filterphoto";
    }

    @ResponseBody
    @GetMapping(value = "photoAccept")
    public int photoAccept(Model model,
                           @RequestParam(name = "photoId")int photoId){
        Photo photo = photoRepository.findById(photoId).get();
        photo.setCondition(2);
        photoRepository.save(photo);
        return 200;
    }


    @ResponseBody
    @GetMapping(value = "photoDecline")
    public int photoDecline(Model model,
                           @RequestParam(name = "photoId")int photoId,
                            @RequestParam(name = "reason", required = false)String reason){
        Photo photo = photoRepository.findById(photoId).get();
        Message message = new Message();
        if(reason!=null)
            message.setText(reason);
        else
            message.setText("图片不合规！");
        message.setPhoto(photo.getPhoto());
        message.setUser(photo.getUser());
        messageRepository.save(message);
        photoRepository.delete(photo);

        return 200;
    }



    @GetMapping(value = "addactivity")
    public String willAddActivity(HttpSession session, Model model){
        return "addactivity";
    }

    @PostMapping(value = "addactivity")
    public String addActivity(HttpSession session, Model model,
                              @RequestParam(name = "actname")String name,
                              @RequestParam(name = "acttext")String text,
                              @RequestParam(name = "photos", required = false) List<Integer> photoIds){
        if(activityRepository.findByName(name)!=null){
            model.addAttribute("error", "已存在同名活动");
        }
        List<Photo> photos = new ArrayList<Photo>();
        try {
            if(photoIds!=null) {
                for (int photoId : photoIds) {
                    photos.add(photoRepository.findById(photoId).get());
                }
            }
            Activity activity = new Activity();
            activity.setPhotos(photos);
            activity.setText(text);
            activity.setName(name);
            activityRepository.save(activity);

        }catch (Exception e){
            model.addAttribute("error", e);
            return "error";
        }
        //model.addAttribute("error", "成功创建");
        return "redirect:/auth";
    }

    @GetMapping(value = "search")
    public String mainSearch(){
        return "search";
    }

    @PostMapping(value = "searchUser")
    public String searchUser(Model model,
                             @RequestParam(name = "username")String username){
        User user = userRepository.findByName(username);
        if(user==null){
            model.addAttribute("error","没有此用户");
            return "error";
        }
        List<User> users = new ArrayList<User>();
        users.add(user);
        model.addAttribute("users",users);

        return "search";
    }


    @RequestMapping(value = "usershut")
    public String userShut(Model model,
                           @RequestParam(name = "userId")int userId){
        Optional<User> opuser = userRepository.findById(userId);
        if(opuser.isPresent()==false){
            model.addAttribute("error","没有此用户");
            return "error";
        }
        System.out.println("设置禁言");
        User user = opuser.get();
        user.setIs_shut(true);
        userRepository.save(user);
        model.addAttribute("error","成功禁言");
        return "search";
    }


}
