package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.common.CommonDataService;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Order;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.OrderDetail;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController extends CommonController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CommonDataService commonDataService;

    @GetMapping(value = "/profile")
    public String profile(Model model, User user) {
        commonDataService.commonData(model, user);
        List<Order> orderByUser = orderRepository.findOrderByUserId(user.getId());
        model.addAttribute("orderByUser", orderByUser);
        return "web/profile";
    }

    @GetMapping("/order/detail/{order_id}")
    public ModelAndView detail(Model model, Principal principal, User user, @PathVariable("order_id") Long id) {

        if (principal != null) {

            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }
        List<OrderDetail> listO = orderDetailRepository.findByOrderId(id);
        model.addAttribute("orderDetail", listO);
        commonDataService.commonData(model, user);

        return new ModelAndView("web/historyOrderDetail");
    }
}
