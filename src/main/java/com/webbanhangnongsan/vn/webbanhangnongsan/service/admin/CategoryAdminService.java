package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;


import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryAdminService {
    @Autowired
    private CategoryRepository CategoryRepository;
    private final static int pageSize = 1;
    @Autowired
    private CategoryRepository categoryRepository;

    //    public List<Category> paginatedCategories(int currentPage) {
//        int offSet = (currentPage - 1) * pageSize;
//        return CategoryRepository.findAll()
//                .stream()
//                .skip(offSet)
//                .limit(pageSize)
//                .toList();
//    }
    public List<Category> paginatedCategories(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Category> searchCategoryList = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getCategoryName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            return categoryRepository.findAll()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchCategoryList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }

//    public int totalPage() {
//        long CategoryQuantity = CategoryRepository.count();
//        return (int) Math.ceil((double) CategoryQuantity / pageSize);
//    }

    public int totalPage(String search) {
        List<Category> searchCategoryList = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getCategoryName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            long categoryQuantity = categoryRepository.count();
            return (int) Math.ceil((double) categoryQuantity / pageSize);
        }
        else {
            int categoryQuantity = searchCategoryList.size();
            return (int) Math.ceil((double) categoryQuantity / pageSize);
        }
    }
}
