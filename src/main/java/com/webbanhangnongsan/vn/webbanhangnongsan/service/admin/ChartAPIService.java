package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.CategoryRepository;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartAPIService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    CategoryRepository categoryRepository;

    public List<Object[]> showDataMonth() {
        return orderDetailRepository.repoWhereMonth();
    }
    public List<Object[]> showDataQuarter() {return orderDetailRepository.repoWhereQUARTER();}
    public List<Object[]> showCategoriesData() {
        return orderDetailRepository.repoWhereCategory();
    }
    public List<Object[]> showCategoriesConsumption() {
        return orderDetailRepository.getCategoryConsumptionByMonth();
    }
}
