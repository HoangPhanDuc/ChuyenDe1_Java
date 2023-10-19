package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import org.springframework.stereotype.Service;

public interface OrderService {
    Order save(ShoppingCart shoppingCart);
}
