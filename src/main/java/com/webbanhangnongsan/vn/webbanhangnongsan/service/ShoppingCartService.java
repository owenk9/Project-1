package com.webbanhangnongsan.vn.webbanhangnongsan.service;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.CartItem;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ShoppingCartService {
    int getCount();

    double getAmount();

    void clear();

    Collection<CartItem> getCartItems();

    void remove(CartItem item);

    void add(CartItem item);

    void remove(Product product);

    CartItem getCartItemById(Long cartItemId);
    void update(CartItem cartItem);
}
