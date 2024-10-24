package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.common.CommonDataService;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.CartItem;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class ProductDetailController extends CommonController{

    @Autowired
    HttpSession session;

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommonDataService commomDataService;

    @GetMapping(value = "productDetail")
    public String productDetail(@RequestParam("id") long id, Model model, User user) {
        Product product = productRepository.findById(id).orElse(null);
        List<Product> relatedProductList = productRepository.list4ProductByCategoryIdExcludingProduct(product.getCategory().getCategoryId(),product.getProductId());
        model.addAttribute("product", product);
        model.addAttribute("relatedProductList", relatedProductList);

        CartItem cartItem = CartItem.builder()
                .id(product.getProductId())  // Đảm bảo rằng `product.getProductId()` không phải là null
                .name(product.getProductName())
                .unitPrice(product.getPrice())
                .product(product)
                .build();

        model.addAttribute("cartItem", cartItem);
        commomDataService.commonData(model, user);
        return "web/shop-details";
    }

}
