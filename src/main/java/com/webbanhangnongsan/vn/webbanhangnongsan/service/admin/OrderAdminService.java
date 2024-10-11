package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;


import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Order;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAdminService {

    @Autowired
    OrderRepository orderRepository;
    private final static int pageSize = 1;
    public List<Order> listAll() { return orderRepository.findAll();}
    public List<Order> paginatedCategories(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Order> searchOrderList = orderRepository.findAll()
                .stream()
                .filter(order -> order.getUser().getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            return orderRepository.findAll()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchOrderList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    public int totalPage(String search) {
        List<Order> searchOrderList = orderRepository.findAll()
                .stream()
                .filter(order -> order.getUser().getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            long categoryQuantity = orderRepository.count();
            return (int) Math.ceil((double) categoryQuantity / pageSize);
        }
        else {
            int categoryQuantity = searchOrderList.size();
            return (int) Math.ceil((double) categoryQuantity / pageSize);
        }
    }
}
