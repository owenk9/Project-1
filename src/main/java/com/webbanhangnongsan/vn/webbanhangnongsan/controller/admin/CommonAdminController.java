package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class CommonAdminController {
    @Autowired
    UserRepository userRepository;

    @ModelAttribute(value = "user")
    public User user(Model model, Principal principal, User user) {
        if (principal != null) {
            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }
        return user;
    }

    @ModelAttribute
    public void addAuthAttributes(Model model, User user) {
        if (user != null && user.getName() != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }
}
