package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.CartItem;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.CategoryRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @ModelAttribute(value = "user")
    public User user(Model model, Principal principal, User user) {
        if (principal != null) {
            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }
        return user;
    }

    @ModelAttribute("categoryList")
    public List<Category> showCategory(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return categoryList;
    }

    @ModelAttribute(value = "numberOfItemsInCart")
    public int numberOfItemsInCart(User user) {
        return shoppingCartService.getCartItems().size();
    }

    @ModelAttribute(value = "totalPrice")
    public double totalPrice(User user) {
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
            totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
        }
        return totalPrice;
    }

    @ModelAttribute
    public void addAuthAttributes(Model model, User user) {
        if (user != null && user.getName() != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", user.getName());
            model.addAttribute("role", user.getRoles());
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }
}
