package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.OrderDetail;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class ChartController extends CommonAdminController{
    private OrderDetailRepository orderDetailRepository;
    @GetMapping("/charts")
    public String Chart(){
        return "admin/charts";
    }
}
