package com.digitalphotolibrary.demo.controller;

import com.digitalphotolibrary.demo.dao.UserRepository;
import com.digitalphotolibrary.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/login")
    public String hello(HttpSession session, Model model){

//        User user = userRepository.findById(1).get();
//        session.setAttribute("username", user.getName());
//        session.setAttribute("user", user);
//        return "redirect:/photolibrary/mainpage";

//        System.out.println("login visit");
//        model.addAttribute("error", "未登录");
        return "login.html";
    }
    @PostMapping(value = "/login")
    public String loginhello(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
//        User user = userRepository.findById(1).get();
//        session.setAttribute("username", user.getName());
//        session.setAttribute("user", user);
//        return "redirect:/photolibrary/mainpage";


        User user = userRepository.findByName(username);
        if(user==null){
            model.addAttribute("error", "用户名错误");
        }else {
            if (user.getPassword().equals(password)) {
                System.out.println("yes");
                session.setAttribute("username", username);
                session.setAttribute("user", user);
                return "redirect:/photolibrary/mainpage";
            } else {
                System.out.println("no");
                model.addAttribute("error", "密码错误");
            }
        }
        return "login";



    }
    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerNew( @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam(value = "mail", required = false) String mail,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "headimg", required = false) MultipartFile imgFile,
                               Model model
                               ){
        if(userRepository.findByName(username)!=null){
            model.addAttribute("error", "用户名已被注册");
        }else {
            User user = new User();
            user.setName(username);
            user.setSex("男");
            user.setPassword(password);
            userRepository.save(user);
            model.addAttribute("error", "注册成功");
        }
        return "register";
    }



}