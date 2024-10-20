package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/dashboard")
    public String Dashboard(Model model) {
        model.addAttribute("top5Users", getTop5Users());
        return "admin/index";
    }
    public List<User> getTop5Users() {
        return userRepository.top5Users();
    }
}

