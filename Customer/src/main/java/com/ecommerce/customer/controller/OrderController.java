package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping("/check-out")
    public String checkOut (Model model, Principal principal) {
        CustomerDto customer = customerService.getCustomer(principal.getName());
            System.out.println("customer info " + customer);
            ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Checkout");
            model.addAttribute("page", "Checkout");
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("grandTotal", cart.getTotalItems());
            return "checkout";
    };

    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String createOrder(
            Principal principal,
            Model model,
            HttpSession session,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {
        Customer customer = customerService.findByUsername(principal.getName());
        CustomerDto customerDto = customerService.getCustomer(principal.getName());
        customerDto.setAddress(address);
        customerDto.setPhone(phone);
        ShoppingCart cart = customer.getCart();
        Order order = orderService.save(cart);
        customerService.update(customerDto);
        session.removeAttribute("totalItems");
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        model.addAttribute("page", "Order Detail");
        model.addAttribute("success", "Add order Successfully");
        return "order-detail";
    }
};