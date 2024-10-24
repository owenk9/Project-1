package com.webbanhangnongsan.vn.webbanhangnongsan.controller;

import com.webbanhangnongsan.vn.webbanhangnongsan.common.CommonDataService;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.*;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.ShoppingCartService;
import com.webbanhangnongsan.vn.webbanhangnongsan.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class CartController extends CommonController{
    @Autowired
    HttpSession session;

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommonDataService commomDataService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    private VNPayService vnPayService;

    public Order orderFinal = new Order();

    @PostMapping("/addToCartFromDetail")
    public String addToCart(@ModelAttribute("cartItem") CartItem cartItem, HttpServletRequest request, Model model, User user) {
        // Xử lý thêm vào giỏ hàng
        System.out.println("Cart Item Details:");
        System.out.println("ID: " + cartItem.getId());
        System.out.println("Name: " + cartItem.getName());
        System.out.println("Unit Price: " + cartItem.getUnitPrice());
        System.out.println("Quantity: " + cartItem.getQuantity());
        System.out.println("Total Price: " + cartItem.getTotalPrice());

        session = request.getSession();
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        if (cartItem != null) {
            shoppingCartService.add(cartItem);
        }
        session.setAttribute("cartItems", cartItems);

        // Lấy lại sản phẩm hiện tại
        Product product = productRepository.findById(cartItem.getId()).orElse(null);
        model.addAttribute("product", product);

        // Cập nhật dữ liệu chung
        commomDataService.commonData(model, user);

        // Trả về cùng một trang
        return "redirect:/productDetail?id=" + cartItem.getId();
    }

    // print cart list to the console
    @GetMapping("/checkCartList")
    public String printCartList(HttpServletRequest request, Model model, User user) {
        // Lấy session hiện tại
        session = request.getSession();

        // Lấy danh sách các mục trong giỏ hàng từ session
        //Collection<CartItem> cartItems = (Collection<CartItem>) session.getAttribute("cartItems");
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        if (cartItems != null && !cartItems.isEmpty()) {
            // In ra thông tin giỏ hàng
            System.out.println("Cart Items:");
            for (CartItem item : cartItems) {
                System.out.println("ID: " + item.getId());
                System.out.println("Name: " + item.getName());
                System.out.println("Unit Price: " + item.getUnitPrice());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Total Price: " + item.getTotalPrice());
                System.out.println("------------");
            }
        } else {
            System.out.println("Cart is empty.");
        }

        // Thêm danh sách này vào model để hiển thị trên giao diện
        model.addAttribute("cartItems", cartItems);

        // Cập nhật dữ liệu chung
        commomDataService.commonData(model, user);

        // Trả về trang hiển thị giỏ hàng
        return "redirect:/products";
    }

    @GetMapping(value = "/addToCart")
    public String add(@RequestParam("productId") Long productId, HttpServletRequest request, Model model) {

        Product product = productRepository.findById(productId).orElse(null);

        session = request.getSession();
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        if (product != null) {
            CartItem cartItem = CartItem.builder()
                    .id(product.getProductId())  // Đảm bảo rằng `product.getProductId()` không phải là null
                    .name(product.getProductName())
                    .unitPrice(product.getPrice())
                    .product(product)
                    .quantity(1)
                    .totalPrice(product.getPrice())
                    .build();
            shoppingCartService.add(cartItem);
        }
        session.setAttribute("cartItems", cartItems);
        model.addAttribute("totalCartItems", shoppingCartService.getCount());

        return "redirect:/products";
    }

    @GetMapping(value = "/cart")
    public String cart(Model model, User user) {

        Order order = new Order();
        model.addAttribute("order", order);

        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", shoppingCartService.getAmount());
        model.addAttribute("NoOfItems", shoppingCartService.getCount());
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
            totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
        }

        double subtotal = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getTotalPrice();
            subtotal += price;
        }
        model.addAttribute("subTotal", subtotal);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCartItems", shoppingCartService.getCount());
        commomDataService.commonData(model, user);

        return "web/shoping-cart";
    }

    @PostMapping("/updateCartItem")
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId,
                                 @RequestParam("quantity") int quantity,
                                 HttpServletRequest request,
                                 Model model) {
        CartItem cartItem = shoppingCartService.getCartItemById(cartItemId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(cartItem.getUnitPrice() * quantity);
            shoppingCartService.update(cartItem);

            session = request.getSession();
            session.setAttribute("cartItems", shoppingCartService.getCartItems());
            model.addAttribute("totalCartItems", shoppingCartService.getCount());
        }

        return "redirect:/cart";
    }

    @GetMapping(value = "/remove/{id}")
    public String remove(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        session = request.getSession();
        if (product != null) {
            CartItem cartItem = CartItem.builder()
                    .id(product.getProductId())  // Đảm bảo rằng `product.getProductId()` không phải là null
                    .name(product.getProductName())
                    .product(product)
                    .build();
            cartItems.remove(session);
            shoppingCartService.remove(cartItem);
        }
        model.addAttribute("totalCartItems", shoppingCartService.getCount());
        return "redirect:/cart";
    }


    @GetMapping(value = "/checkout")
    public String checkOut(Model model, User user) {

        Order order = new Order();
        model.addAttribute("order", order);

        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", shoppingCartService.getAmount());
        model.addAttribute("NoOfItems", shoppingCartService.getCount());
        double subtotal = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getTotalPrice();
            subtotal += price;
        }

        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
            totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
        }
        model.addAttribute("subTotal", subtotal);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCartItems", shoppingCartService.getCount());
        commomDataService.commonData(model, user);

        return "web/checkout";
    }

    @PostMapping(value = "/checkout")
    @Transactional
    public String checkOut(Model model,Order order,HttpServletRequest request,User user){
        String checkOut = request.getParameter("checkOut");
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
            totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
        }
        BeanUtils.copyProperties(order, orderFinal);
        if(StringUtils.equals(checkOut,"cod")){
            session = request.getSession();
            LocalDateTime date = LocalDateTime.now();
            order.setOrderDate(date);
            order.setStatus(0);
            order.getOrderId();
            order.setAmount(totalPrice);
            order.setUser(user);

            orderRepository.save(order);

            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                double unitPrice = cartItem.getProduct().getPrice();
                orderDetail.setPrice(unitPrice);
                orderDetailRepository.save(orderDetail);
            }
            try {
                commomDataService.sendSimpleEmail(user.getEmail(), "Order Success", "Your order has been placed successfully", cartItems, totalPrice, order);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            shoppingCartService.clear();
            session.removeAttribute("cartItems");
            model.addAttribute("orderId", order.getOrderId());
            return "redirect:/checkout_success";
        }
        else{
            String orderInfo = user.getName() + " thanh toan luc " + LocalDateTime.now().toString();
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            int orderTotal = (int) totalPrice;
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }
    }

    @GetMapping(value = "/checkout_success")
    public String checkoutSuccess(Model model, User user) {
        commomDataService.commonData(model, user);

        return "web/checkout_success";

    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model,User user){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        if (totalPrice != null && totalPrice.length() > 2) {
            // Loại bỏ 2 ký tự cuối của chuỗi
            totalPrice = totalPrice.substring(0, totalPrice.length() - 2);
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPriceVNPay", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus == 1){
            Collection<CartItem> cartItems = shoppingCartService.getCartItems();
            double totalPrice1 = 0;
            for (CartItem cartItem : cartItems) {
                double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
                totalPrice1 += price - (price * cartItem.getProduct().getDiscount() / 100);
            }
            session = request.getSession();
            LocalDateTime date = LocalDateTime.now();
            orderFinal.setOrderDate(date);
            orderFinal.setStatus(2);
            orderFinal.getOrderId();
            orderFinal.setUser(user);
            orderFinal.setAmount(totalPrice1);
            orderRepository.save(orderFinal);

            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setOrder(orderFinal);
                orderDetail.setProduct(cartItem.getProduct());
                double unitPrice = cartItem.getProduct().getPrice();
                orderDetail.setPrice(unitPrice);
                orderDetailRepository.save(orderDetail);
            }
            try {
                commomDataService.sendSimpleEmail(user.getEmail(), "Order Success", "Your order has been placed successfully", cartItems, totalPrice1, orderFinal);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            shoppingCartService.clear();
            session.removeAttribute("cartItems");
            orderFinal = new Order();
            return "web/ordersuccess";
        }
        else return "web/orderfail";
    }



}
