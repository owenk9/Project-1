package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class IndexController extends CommonAdminController{
    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }
}
