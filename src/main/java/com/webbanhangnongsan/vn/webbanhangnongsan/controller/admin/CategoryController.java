package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;


import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.CategoryRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.admin.CategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController extends CommonAdminController{
    @Value("${upload.path}")
    private String pathUploadImage;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryAdminService categoryAdminService;
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

    @GetMapping("/category")
    public String Category(Model model) {
        getData(model);
        paginatedCategories(model, 1, "");
        return "admin/category";
    }
    public void getData(Model model){
        List<Category> showCategory = categoryRepository.findAll();
        model.addAttribute("showCategory", showCategory);
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        Category category = new Category();
        model.addAttribute("adminCategory", category);

        return "admin/forms/add_new_category";
    }
    @PostMapping("/addCategory")
    public String addNewCategory(@ModelAttribute("adminCategory") Category category, ModelMap model, @RequestParam("file") MultipartFile file){
        try {
            // Save the file to the upload path
            File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            // Set the uploaded file name to the category entity
            category.setCategoryImage(file.getOriginalFilename());
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
            return "redirect:/admin/category"; // Redirect to prevent form resubmission
        }

        // Save the category to the repository
        Category savedCategory = categoryRepository.save(category);

        if (savedCategory != null) {
            model.addAttribute("message", "Category added successfully");
            model.addAttribute("category", category);
        } else {
            model.addAttribute("message", "Category addition failed");
            model.addAttribute("category", category);
        }

        return "redirect:/admin/category";

    }

    @GetMapping("/editCategory/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/admin/category";
        }
        model.addAttribute("editCategory", category);
        return "admin/forms/edit_category";
    }


    @PostMapping("/editCategory")
    public String editProduct(@ModelAttribute("editCategory") Category category, @RequestParam("file") MultipartFile file, ModelMap model) {
        try {
            // Check if a new file is uploaded
            if (!file.isEmpty()) {
                // Save the new image file
                File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close();

                // Set the new file name to the category entity
                category.setCategoryImage(file.getOriginalFilename());
            } else {
                // If no new file is uploaded, retain the old image
                Category existingCategory = categoryRepository.findById(category.getCategoryId()).orElse(null);
                if (existingCategory != null) {
                    category.setCategoryImage(existingCategory.getCategoryImage());
                }
            }

            // Save the updated category information
            Category updatedCategory = categoryRepository.save(category);
            if (updatedCategory != null) {
                model.addAttribute("message", "Category updated successfully");
                model.addAttribute("category", category);
            } else {
                model.addAttribute("message", "Category update failed");
                model.addAttribute("category", category);
            }

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String delCategory(@PathVariable("id") Long id, Model model) {
        categoryRepository.deleteById(id);
        model.addAttribute("message", "Xóa loại sản phẩm thành công!");
        return "redirect:/admin/category";
    }

//    @GetMapping("/paginationCategories")
//    public String paginatedCategories(Model model, @RequestParam("currentPage") int currentPage) {
//        List<Category> categoryList = categoryAdminService.paginatedCategories(currentPage);
//        int totalPage = categoryAdminService.totalPage();
//        model.addAttribute("paginatedCategories", categoryList);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", totalPage);
//        return "admin/category";
//    }

    @PostMapping("/paginationCategories")
    public String handleSearch(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin1/paginationCategories"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationCategories")
    public String paginatedCategories(Model model,
                                      @RequestParam("currentPage") int currentPage,
                                      @RequestParam("search") String search) {
        List<Category> categoryList = categoryAdminService.paginatedCategories(search, currentPage);
        int totalPage = categoryAdminService.totalPage(search);
        model.addAttribute("search", search);
        model.addAttribute("paginatedCategories", categoryList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        return "admin/category";
    }
}
