package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Cart;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartRepository cartRepository;
    private final CustomerService customerService;

    @Override
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();

        if(shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

        Set<Cart> cartList = shoppingCart.getCartItems();

        Cart cartItem = find(cartList, productDto.getId());
        Product product = transfer(productDto);

        double unitPrice = productDto.getCostPrice();
        double salePrice = productDto.getSalePrice();

        int itemQuantity = 0;

        if(cartList == null) {
            cartList = new HashSet<>();
            if(cartItem == null) {
                cartItem = new Cart();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setSalePrice(salePrice);
                cartList.add(cartItem);
                cartRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() +quantity;
                cartItem.setQuantity(itemQuantity);
                cartRepository.save(cartItem);
            }
        } else {
            if(cartItem == null) {
                cartItem = new Cart();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setSalePrice(salePrice);
                cartList.add(cartItem);
                cartRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() +quantity;
                cartItem.setQuantity(itemQuantity);
                cartRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartList);

        double totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setCustomer(customer);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<Cart> cartItemList = shoppingCart.getCartItems();
        Cart item = find(cartItemList, productDto.getId());
        cartItemList.remove(item);
        cartRepository.delete(item);

        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);

        return shoppingCartRepository.save(shoppingCart);
    }

    private Cart find(Set<Cart> cartItems, long productId) {
        if(cartItems == null) {
            return null;
        }
        Cart cartItem = null;
        for(Cart cart : cartItems) {
            if(cart.getProduct().getId() == productId) {
                cartItem = cart;
            }
        }
        return cartItem;
    }
    private Product transfer(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.set_active(productDto.isActive());
        product.set_deleted(productDto.isDeleted());
        product.setCategory(productDto.getCategory());

        return product;
    }

    private double totalPrice(Set<Cart> cartItem) {
        double total = 0.0;
        for(Cart item : cartItem) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        return total;
    }

    private int totalItem(Set<Cart> cartItem) {
        int total = 0;
        for(Cart item : cartItem) {
            total += item.getQuantity();
        }
        return total;
    }

    private int countItem(Set<Cart> cartItem) {
        int total = 0;
        for(Cart item : cartItem) {
//            total += item.;
        }
        return total;
    }
}
