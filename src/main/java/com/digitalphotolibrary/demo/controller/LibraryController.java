package com.digitalphotolibrary.demo.controller;

import com.digitalphotolibrary.demo.dao.*;
import com.digitalphotolibrary.demo.entity.Photo;
import com.digitalphotolibrary.demo.entity.PhotoLibrary;
import com.digitalphotolibrary.demo.entity.User;

import com.digitalphotolibrary.demo.service.impl.PhotoFilterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photolibrary")
public class LibraryController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoLibraryRepository photoLibraryRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PhotoFilterServiceImpl photoFilterServiceImpl;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping(value = "/mainpage")
    public String mainPage(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("messages",messageRepository.findByUser(user));
        //model.addAttribute("username", username);
        model.addAttribute("librarys", photoLibraryRepository.findByUser(user));

        return "mainpage";
    }

    @GetMapping(value = "/addlibrary")
    public String willAddLibrary(HttpSession session, Model model){
        //User user = (User) session.getAttribute("user");
        return "addLibrary";
    }

    @PostMapping(value = "/addlibrary")
    public String addLibrary(HttpSession session, Model model,
                             @RequestParam("libraryName") String libraryName,
                             @RequestParam("isPrivate") Boolean isPrivate,
                             @RequestParam(value = "desctription" , required = false) String desctription
                             ){

        User user = (User) session.getAttribute("user");
        List<PhotoLibrary> photoLibraries = photoLibraryRepository.findByUser(user);
        for (PhotoLibrary pl: photoLibraries) {
            if(pl.getName().equals(libraryName)){
                model.addAttribute("error","相册名重复！");
                return "addlibrary";
            }
        }
        PhotoLibrary photoLibrary = new PhotoLibrary();
        photoLibrary.setName(libraryName);
        photoLibrary.setUser(user);
        photoLibrary.setPrivate(isPrivate);
        if(desctription!=null)
            photoLibrary.setDesctription(desctription);
        photoLibraryRepository.save(photoLibrary);
        model.addAttribute("error", "创建成功！");
        return "addlibrary";
    }




    @GetMapping()
    public String libraryDetail(HttpSession session, Model model,
                                @RequestParam(name = "libraryId",required = false, defaultValue = "-1") int libraryId){
        if(libraryId==-1&&session.getAttribute("library_id")!=null){
            libraryId = (int)session.getAttribute("library_id");
            session.removeAttribute("library_id");
        }
        Optional<PhotoLibrary> oplibrary = photoLibraryRepository.findById(libraryId);
        if (oplibrary.isPresent() == false) {
            model.addAttribute("error", "没有此图库");
            return "error";
        } else {
            PhotoLibrary library = oplibrary.get();
            User user = (User) session.getAttribute("user");
            if (user.getId() != library.getUser().getId()) {
                if (library.getPrivate() == true) {
                    model.addAttribute("error", "没有权限访问");
                    return "error";
                }
            }
            List<Photo> photos = photoRepository.findByPhotoLibraryAndCondition(library, 2);
            model.addAttribute("photoLibrary", library);
            model.addAttribute("photos", photos);
            return "librarydetail";
        }
    }

    @PostMapping()
    public String addToLibrary(HttpSession session, Model model,
                               int libraryId,
                               @RequestParam("img") MultipartFile img) throws IOException {
        Optional<PhotoLibrary> oplibrary = photoLibraryRepository.findById(libraryId);
        if(oplibrary.isPresent() == false) {
            model.addAttribute("error", "没有此图库");
            return "error";
        }
        PhotoLibrary library = oplibrary.get();
        User user = (User)session.getAttribute("user");
        if(user.getIs_shut()==true&&library.getPrivate()==false){
            model.addAttribute("error","你已经被禁言，只能上传私人相册");
            return "error";
        }

        Photo photo = new Photo();
        photo.setUser((User)session.getAttribute("user"));
        try {
            photo.setPhoto(img.getBytes());
        } catch (IOException e) {
            model.addAttribute("error","添加失败，文件损坏");
            e.printStackTrace();
            return "error";
        }
        photo.setPhotoLibrary(library);
//        PhotoFilterServiceImpl photoFilterServiceImpl = new PhotoFilterServiceImpl();
        photoFilterServiceImpl.save(photo);
        model.addAttribute("error","添加成功");
        session.setAttribute("library_id", library.getId());
        return "redirect:/photolibrary";
    }
}
