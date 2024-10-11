package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.common.CommonDataService;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.FavoriteRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController extends CommonController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    FavoriteRepository favoriteRepository;

    @GetMapping
    public String home(HttpServletRequest request, Model model, User user) {
        commonDataService.commonData(model, user);
        List<Product> productList = productRepository.top8Products();
        model.addAttribute("productList", productList);

        return "web/index";
    }


}
