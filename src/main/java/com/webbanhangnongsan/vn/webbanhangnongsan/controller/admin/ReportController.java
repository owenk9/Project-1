package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.OrderDetail;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.admin.ReportAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReportController extends CommonAdminController{

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ReportAdminService reportAdminService;

    // Statistics by product sold
    @GetMapping(value = "/reports")
    public String report(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

//        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repo();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedRepo(model, 1, "");


        return "admin/statistical";
    }

    // Statistics by category sold
    @GetMapping(value = "/reportCategory")
    public String reportCategory(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

//        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repoWhereCategory();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedCategoryRepo(model, 1, "");

        return "admin/statistical";
    }

    // Statistics of products sold by year
    @GetMapping(value = "/reportYear")
    public String reportYear(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

//        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repoWhereYear();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedYearRepo(model, 1, "");

        return "admin/statistical";
    }

    // Statistics of products sold by month
    @GetMapping(value = "/reportMonth")
    public String reportMonth(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repoWhereMonth();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedMonthRepo(model, 1, "");

        return "admin/statistical";
    }

    // Statistics of products sold by quarter
    @GetMapping(value = "/reportQuarter")
    public String reportQuarter(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

//        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repoWhereQUARTER();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedQuarterRepo(model, 1, "");

        return "admin/statistical";
    }

    // Statistics of products sold by user
    @GetMapping(value = "/reportUser")
    public String reportUser(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

//        OrderDetail orderDetail = new OrderDetail();
//        model.addAttribute("orderDetail", orderDetail);
//        List<Object[]> listReportCommon = orderDetailRepository.repoWhereQUARTER();
//        model.addAttribute("listReportCommon", listReportCommon);
        paginatedUserRepo(model, 1, "");

        return "admin/statistical";
    }

    // paginating product sold
    @PostMapping("/paginationRepo")
    public String handleSearchRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationRepo")
    public String paginatedRepo(Model model,
                                      @RequestParam("currentPage") int currentPage,
                                      @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.paginatedRepo(search, currentPage);
        int totalPage = reportAdminService.totalRepoPage(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("link", "paginationRepo");
        model.addAttribute("title", "Product report");

        return "admin/statistical";
    }

    // paginating category sold
    @PostMapping("/paginationCategoryRepo")
    public String handleSearchCategoryRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationCategoryRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationCategoryRepo")
    public String paginatedCategoryRepo(Model model,
                                @RequestParam("currentPage") int currentPage,
                                @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.repoWhereCategory(search, currentPage);
        int totalPage = reportAdminService.totalRepoWhereCategory(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("link", "paginationCategoryRepo");
        model.addAttribute("title", "Category report");

        return "admin/statistical";
    }

    // paginating year sold
    @PostMapping("/paginationYearRepo")
    public String handleSearchYearRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationYearRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationYearRepo")
    public String paginatedYearRepo(Model model,
                                        @RequestParam("currentPage") int currentPage,
                                        @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.repoWhereYear(search, currentPage);
        int totalPage = reportAdminService.totalRepoWhereYear(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("title", "Year report");
        model.addAttribute("link", "paginationYearRepo");

        return "admin/statistical";
    }

    // paginating quarter sold
    @PostMapping("/paginationQuarterRepo")
    public String handleSearchQuarterRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationQuarterRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationQuarterRepo")
    public String paginatedQuarterRepo(Model model,
                                    @RequestParam("currentPage") int currentPage,
                                    @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.repoWhereQUARTER(search, currentPage);
        int totalPage = reportAdminService.totalRepoWhereQUARTER(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("title", "Quarter report");
        model.addAttribute("link", "paginationQuarterRepo");

        return "admin/statistical";
    }

    // paginating month sold
    @PostMapping("/paginationMonthRepo")
    public String handleSearchMonthRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationMonthRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationMonthRepo")
    public String paginatedMonthRepo(Model model,
                                       @RequestParam("currentPage") int currentPage,
                                       @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.repoWhereMonth(search, currentPage);
        int totalPage = reportAdminService.totalRepoWhereMonth(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("title", "Month report");
        model.addAttribute("link", "paginationMonthRepo");

        return "admin/statistical";
    }

    // pagination user sold
    @PostMapping("/paginationUserRepo")
    public String handleSearchUserRepo(@RequestParam("search") String search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        redirectAttributes.addAttribute("currentPage", 1); // Đặt trang mặc định là 1
        return "redirect:/admin/paginationUserRepo"; // Chuyển hướng đến phương thức GET searchProducts
    }

    @GetMapping("/paginationUserRepo")
    public String paginatedUserRepo(Model model,
                                     @RequestParam("currentPage") int currentPage,
                                     @RequestParam("search") String search) {
        List<Object[]> repoList = reportAdminService.reportCustomer(search, currentPage);
        int totalPage = reportAdminService.totalRepoWhereMonth(search);
        int pageSize = reportAdminService.getPageSize();
        model.addAttribute("search", search);
        model.addAttribute("paginatedRepo", repoList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("title", "User report");
        model.addAttribute("link", "paginationUserRepo");

        return "admin/statistical";
    }
}
