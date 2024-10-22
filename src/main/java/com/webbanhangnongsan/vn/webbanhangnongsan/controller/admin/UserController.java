package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController extends CommonAdminController{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAdminService userAdminService;

    @GetMapping("/user")
    public String User(Model model) {
        getData(model);
        paginatedUsers(model, 1, "");
        return "admin/user";
    }

    public void getData(Model model){
        List<User> showUser = userRepository.findAll();
        model.addAttribute("showUser", showUser);
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("adminUser", new User());
        return "admin/forms/add_new_user";
    }

    @PostMapping("/addUser")
    public String addNewUser(@ModelAttribute("adminUser") User user, Model model) {
        try {

            User savedUser = userRepository.save(user);
            model.addAttribute("message", "Thêm người dùng thành công");
            model.addAttribute("user", savedUser);
        } catch (Exception e) {
            model.addAttribute("message", "Thêm người dùng thất bại: " + e.getMessage());
            model.addAttribute("user", user);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            model.addAttribute("message", "Không tìm thấy người dùng");
            return "redirect:/admin/user";
        }
        model.addAttribute("editUser", user);
        return "admin/forms/edit_user";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("editUser") User user, Model model) {
        try {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser == null) {
                model.addAttribute("message", "Không tìm thấy người dùng");
                return "redirect:/admin/user";
            }
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            model.addAttribute("message", "Cập nhật người dùng thành công");
        } catch (Exception e) {
            model.addAttribute("message", "Cập nhật người dùng thất bại: " + e.getMessage());
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/deleteUser/{id}")
    public String delCategory(@PathVariable("id") Long id, Model model) {
        try {
            userRepository.deleteById(id);
            model.addAttribute("message", "Xóa người dùng thành công!");
        } catch (Exception e) {
            model.addAttribute("message", "Xóa người dùng thất bại: " + e.getMessage());
        }
        return "redirect:/admin/user";
    }

//    @GetMapping("/paginationUsers")
//    public String paginatedUsers(Model model, @RequestParam("currentPage") int currentPage) {
//        List<User> userList = userAdminService.paginatedUsers(currentPage);
//        int totalPage = userAdminService.totalPage();
//        model.addAttribute("paginatedUsers", userList);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", totalPage);
//        return "admin/user";
//    }

    @PostMapping("/paginationUsers")
    public String handleSearch(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationUsers"; // Chuyển hướng đến phương thức GET searchProducts
    }
    @GetMapping("/paginationUsers")
    public String paginatedUsers(Model model,
                                 @RequestParam("currentPage") int currentPage,
                                 @RequestParam("search") String search) {
        List<User> userList = userAdminService.paginatedUsers(search, currentPage);
        int totalPage = userAdminService.totalPage(search);
        model.addAttribute("search", search);
        model.addAttribute("paginatedUsers", userList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        return "admin/user";
    }


}