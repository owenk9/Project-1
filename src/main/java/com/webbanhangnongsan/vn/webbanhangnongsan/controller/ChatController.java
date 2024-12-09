package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.ChatMessage;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> receiveMessage(@RequestBody ChatMessage userMessage) {
        ChatMessage botResponse = chatbotService.processMessage(userMessage);
        return ResponseEntity.ok(botResponse);
    }
}
