package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.common.CommonDataService;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.FavoriteRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ShopController extends CommonController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    ProductService productService;

    @GetMapping(value = "/products")
    public String shop(HttpServletRequest request, Model model, User user) {
        commonDataService.commonData(model, user);
        List<Product> productList = productService.listProductFiltered(0, 0, 100000,1);
        int productFound = productService.productFound(0, 0, 100000);
        List<Category> categories = categoryRepository.findAll();
        int numPages = productService.numPage(0, 0, 100000);
        model.addAttribute("productList", productList);
        model.addAttribute("minAmount", 0);
        model.addAttribute("maxAmount", 100000);
        model.addAttribute("categoryId", 0);
        model.addAttribute("page", 1);
        model.addAttribute("numPages", numPages);
        model.addAttribute("categories", categories);
        model.addAttribute("productFound", productFound);
        return "web/shop-grid";
    }

    @GetMapping("/filterByPrice")
    public String filterByPrice(@RequestParam("minamount") String minAmount,
                                @RequestParam("maxamount") String maxAmount,
                                Model model) {
        System.out.println("Min amount: " + minAmount);
        System.out.println("Max amount: " + maxAmount);

        // Thêm giá trị minAmount và maxAmount vào model
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("maxAmount", maxAmount);


        // Thực hiện thêm logic xử lý ở đây (ví dụ: lọc sản phẩm theo giá)

        return "web/shop-grid"; // Trả về trang kết quả lọc
    }

    @GetMapping("filteredProducts")
    public String filteredProducts(@RequestParam("minamount") String minAmount,
                                   @RequestParam("maxamount") String maxAmount,
                                   @RequestParam("categoryId") long categoryId,
                                   @RequestParam("page") int page,
                                   Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<Product> productList = productService.listProductFiltered(categoryId, Integer.parseInt(minAmount), Integer.parseInt(maxAmount),page);
        int numPages = productService.numPage(categoryId, Integer.parseInt(minAmount), Integer.parseInt(maxAmount));
        int productFound = productService.productFound(categoryId, Integer.parseInt(minAmount), Integer.parseInt(maxAmount));
        System.out.println("Min amount: " + minAmount);
        System.out.println("Max amount: " + maxAmount);
        System.out.println("Category Id: " + categoryId);
        // Thêm giá trị minAmount và maxAmount vào model
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        model.addAttribute("page", page);
        model.addAttribute("numPages", numPages);
        model.addAttribute("productFound", productFound);
        // Thực hiện thêm logic xử lý ở đây (ví dụ: lọc sản phẩm theo giá)

        return "web/shop-grid"; // Trả về trang kết quả lọc
    }

    @GetMapping("searchProducts")
    public String searchProducts(@RequestParam("search") String search,
                                 @RequestParam("page") int page,
                                 Model model) {
        List<Product> productList = productService.listProductSearch(search,page);
        int numPages = productService.numPageSearch(search);
        System.out.println("numPages: " + numPages);
        model.addAttribute("productList", productList);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("numPages", numPages);
        return "web/shop-search"; // Trả về trang kết quả lọc
    }

    @PostMapping("/searchProducts")
    public String handleSearch(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("page", 1); // Đặt trang mặc định là 1
        return "redirect:/searchProducts"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @ModelAttribute(value = "saleOffProducts")
    public List<Product> saleOffProducts(){
        return productRepository.listSaleOffProducts();
    }

}

