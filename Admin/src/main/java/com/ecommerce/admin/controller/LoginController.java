package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/index")
    public String homePage() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-pass")
    public  String forgotPass(Model model) {
        return "forgot-pass";
    };

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result, Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if(result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            };

            String username= adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if(admin != null) {
                redirectAttributes.addFlashAttribute("message", "Email already exists");
                System.out.println("admin not null");
                return "register";
            };

            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Registration Successfully");
            } else {
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Does not match the password");
                System.out.println("error password");
                return "regiser";
            }



        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "OOPS! Something went wrong!!!");
            ex.printStackTrace();
        }
        return "register";
    }
}
