package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto")CustomerDto customerDto,
                                  BindingResult result, Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            if(result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }

            Customer customer = customerService.findByUserName(customerDto.getUsername());
            if(customer != null) {
                model.addAttribute("errorUsername", "Username already exists");
                model.addAttribute("error", "Email already exists");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            if(customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerDto.setConfirmPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                redirectAttributes.addFlashAttribute("success", "Register Successfully");
                System.out.println("Customer Info: " + customerDto);
                return "register";
            } else if(!customerDto.getPassword().equals(customerDto.getConfirmPassword())){
                model.addAttribute("password", "Confirm password does not match");
                System.out.println("Confirm password does not match");
                model.addAttribute("customerDto", customerDto);
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Something went wrong!!");
            System.out.println("Something went wrong!!");
            model.addAttribute("customerDto", customerDto);
            ex.printStackTrace();
        }
        return "register";
    }
}
