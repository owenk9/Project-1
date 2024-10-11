package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.OrderDetail;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReportController extends CommonAdminController{

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    // Statistics by product sold
    @GetMapping(value = "/reports")
    public String report(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("orderDetail", orderDetail);
        List<Object[]> listReportCommon = orderDetailRepository.repo();
        model.addAttribute("listReportCommon", listReportCommon);

        return "admin/statistical";
    }

    // Statistics by category sold
    @GetMapping(value = "/reportCategory")
    public String reportCategory(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("orderDetail", orderDetail);
        List<Object[]> listReportCommon = orderDetailRepository.repoWhereCategory();
        model.addAttribute("listReportCommon", listReportCommon);

        return "admin/statistical";
    }

    // Statistics of products sold by year
    @GetMapping(value = "/reportYear")
    public String reportYear(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("orderDetail", orderDetail);
        List<Object[]> listReportCommon = orderDetailRepository.repoWhereYear();
        model.addAttribute("listReportCommon", listReportCommon);

        return "admin/statistical";
    }

    // Statistics of products sold by month
    @GetMapping(value = "/reportMonth")
    public String reportMonth(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("orderDetail", orderDetail);
        List<Object[]> listReportCommon = orderDetailRepository.repoWhereMonth();
        model.addAttribute("listReportCommon", listReportCommon);

        return "admin/statistical";
    }

    // Statistics of products sold by quarter
    @GetMapping(value = "/reportQuarter")
    public String reportQuarter(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("orderDetail", orderDetail);
        List<Object[]> listReportCommon = orderDetailRepository.repoWhereQUARTER();
        model.addAttribute("listReportCommon", listReportCommon);

        return "admin/statistical";
    }
}
