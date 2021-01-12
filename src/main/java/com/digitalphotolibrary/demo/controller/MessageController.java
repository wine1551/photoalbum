package com.digitalphotolibrary.demo.controller;

import com.digitalphotolibrary.demo.dao.MessageRepository;
import com.digitalphotolibrary.demo.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @ResponseBody
    @GetMapping("/delete")
    public int delete(@RequestParam(name = "messageId")int messageId){
        Message message = messageRepository.findById(messageId).get();
        messageRepository.delete(message);
        return 200;
    }
}
