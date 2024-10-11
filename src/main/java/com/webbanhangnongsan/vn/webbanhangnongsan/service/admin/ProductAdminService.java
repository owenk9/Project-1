package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;


import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PrinterURI;
import java.util.List;

import static java.util.Locale.filter;

@Service
public class ProductAdminService {
    @Autowired
    private ProductRepository productRepository;

    private final static int pageSize = 1;

    // Xử lý phân trang bao gồm search và paaan trang nội dung
    public List<Product> paginatedProducts(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        // Lấy danh sách sản phẩm theo từ khóa tìm kiếm
        List<Product> searchProductList = productRepository.findAll()
                .stream()
                .filter(product -> product.getProductName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        // Kiem soat so luong san pham
        for(Product product: searchProductList) {
            product.setQuantity(quantityControl(product.getQuantity()));
        }
        // Thanh search rôỗng thì trả về tất cả sản phẩm
        if (search == "") {
            return productRepository.findAll()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchProductList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }

    public int totalPage(String search) {
        List<Product> searchProductList = productRepository.findAll()
                .stream()
                .filter(product -> product.getProductName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            long productQuantity = productRepository.count();
            return (int) Math.ceil((double) productQuantity / pageSize);
        }
        else {
            int productQuantity = searchProductList.size();
            return (int) Math.ceil((double) productQuantity / pageSize);
        }
    }

    public int quantityControl(int productQuantity) {
        if (productQuantity <= 0) {
            return 0;
        }
        return productQuantity;
    }
}
