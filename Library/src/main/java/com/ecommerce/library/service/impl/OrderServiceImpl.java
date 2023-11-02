package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CartDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.ShoppingCartDto;
import com.ecommerce.library.model.Cart;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailRepository orderDetailRepository;

    private final OrderRepository orderRepository;

    private final ShoppingCartService shoppingCartService;

    @Override
    public Order save(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setQuantity(shoppingCart.getTotalItems());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for(Cart cart : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
//            ProductDto productDto = new ProductDto();
//            productDto.setId(cartDto.getProduct().getId());
//            productDto.setName(cartDto.getProduct().getName());
//            productDto.setDescription(cartDto.getProduct().getDescription());
//            productDto.setCostPrice(cartDto.getProduct().getCostPrice());
//            productDto.setSalePrice(cartDto.getProduct().getSalePrice());
//            productDto.setCurrentQuantity(cartDto.getProduct().getCurrentQuantity());
//            productDto.setCategory(cartDto.getProduct().getCategory());
//            productDto.setImage(cartDto.getProduct().getImage());

            orderDetail.setProduct(cart.getProduct());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
        shoppingCartService.deletedCartById(shoppingCart.getId());

        return orderRepository.save(order);
    };
}
