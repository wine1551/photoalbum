package com.digitalphotolibrary.demo.controller;

import com.digitalphotolibrary.demo.dao.CommentRepository;
import com.digitalphotolibrary.demo.dao.PhotoRepository;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.PhotoLibrary;
import com.digitalphotolibrary.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping()
    public String photoDetail(HttpSession session, Model model,
                              @RequestParam(name = "photoId",required = false, defaultValue = "-1") int photoId) {
        if(photoId==-1&&session.getAttribute("photo_id")!=null){
            photoId = (int)session.getAttribute("photo_id");
            session.removeAttribute("photo_id");
        }
        Optional<Photo> opphoto = photoRepository.findById(photoId);
        if(opphoto.isPresent() == false){
            model.addAttribute("error", "没有此照片");
            return "error";
        }else{
            Photo photo = opphoto.get();
            PhotoLibrary library = photo.getPhotoLibrary();
            int nowUserId = ((User)session.getAttribute("user")).getId();
            if(photo.getUser().getId()!=nowUserId&&library.getPrivate()==true){
                model.addAttribute("error","没有权限访问！");
                return "error";
            }
            model.addAttribute("comments",commentRepository.findByPhoto(photo));
            model.addAttribute("photo", photo);
            return "photodetail";
        }

    }

    @ResponseBody
    @GetMapping("/likes")
    public int addLike(HttpSession session,@RequestParam(name = "photoId")int photoId){
        Optional<Photo> opphoto = photoRepository.findById(photoId);
        if(opphoto.isPresent()==false)return -1;
        Photo p = opphoto.get();
        if(session.getAttribute("isLike"+photoId)!=null)return p.getLikes();
        session.setAttribute("isLike"+photoId, 1);
        p.like();
        photoRepository.save(p);
        return p.getLikes();
    }


}
