package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping(value = "/test-login")
    public String testLogin(Model model) {
        return "web/login-test";
    }

    @GetMapping(value = "/test-register")
    public String testRegister(Model model) {
        return "web/register-test";
    }


    @GetMapping(value = "/test-checkout")
    public String checkoutTest(Model model) {
        return "web/checkout";
    }

    @GetMapping(value = "/test-cart")
    public String cartTest(Model model) {
        return "web/shoping-cart";
    }

    @GetMapping(value = "/test-shop")
    public String shopTest(Model model) {
        return "web/shop-grid";
    }

    @GetMapping(value = "/test-detail")
    public String detailTest(Model model) {
        return "web/shop-details";
    }

    @GetMapping(value = "/test-profile")
    public String profileTest(Model model) {
        return "web/profile";
    }

}
