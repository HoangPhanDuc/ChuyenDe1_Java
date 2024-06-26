package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            if(cart == null) {
                System.out.println("Cart Null");
                model.addAttribute("check", true);
            }
            if(cart != null ) {
                System.out.println("Cart Size " + cart.getCartItems().size());
                System.out.println("Cart Info " + cart);
                model.addAttribute("grandTotal", cart.getTotalPrice());
            }

            model.addAttribute("shoppingCart", cart);
            model.addAttribute("title", "Cart");
            return "cart";
        }
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        ProductDto productDto = productService.getReferenceById(id);
        if(principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());

            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "redirect:" + request.getHeader("Referer");
    };

    @RequestMapping(value = "/add-to-cart", method = RequestMethod.POST, params = "action=buy")
    public String buyNow(@RequestParam("id") long productId,
                         @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                         Model model,
                         Principal principal,
                         HttpSession session) {
        ProductDto productDto = productService.getReferenceById(productId);
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());

        model.addAttribute("shoppingCart", shoppingCart);
        return "checkout";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") long productId,
            Principal principal,
            Model model) {
        try {
            ProductDto productDto = productService.getReferenceById(productId);
            String username = principal.getName();
            System.out.println(username + "Deleted Item" + productId);
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            System.out.println(shoppingCart);
            return "redirect:/cart";
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/cart";
    }

//    @PutMapping("/updateQuantity")
//    public String updateQuantityInc(@RequestParam("quantity") int quantity, Model model) {
//        cartService.IncreaseProducts()
//        return "redirect:/cart";
//    }
}
