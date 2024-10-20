package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartAPIService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public List<Object[]> showDataMonth() {
        return orderDetailRepository.repoWhereMonth();
    }
}
