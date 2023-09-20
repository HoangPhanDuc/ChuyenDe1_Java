package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model) {
        List<ProductDto> products = productService.allProduct();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        System.out.println(products.size());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "products";
    };

    @PostMapping("/save-product")
    public String saveProduct (@ModelAttribute("productDto")ProductDto productDto,
                               @RequestParam("imageProduct")MultipartFile imageProduct,
                               RedirectAttributes attributes) {
        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Added Product Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Something went wrong!!");
        }
        return "redirect:/products";
    }

//    @GetMapping("/products/{pageNo}")
//    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
//        Page<ProductDto> products = productService.getAllProducts(pageNo);
//        model.addAttribute("title", "Manage Products");
//        model.addAttribute("size", products.getSize());
//        model.addAttribute("products", products);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", products.getTotalPages());
//        return "products";
//    }
//
//    @GetMapping("/search-products/{pageNo}")
//    public String searchProduct(@PathVariable("pageNo") int pageNo,
//                                @RequestParam(value = "keyword") String keyword,
//                                Model model
//    ) {
//        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
//        model.addAttribute("title", "Result Search Products");
//        model.addAttribute("size", products.getSize());
//        model.addAttribute("products", products);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", products.getTotalPages());
//        return "product-result";
//
//    }
//
    @GetMapping("/add-product")
    public String addProductPage(Model model) {
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findByActive();
//        System.out.println(categories);
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "redirect:/login";
//        }
        return "add-product";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Update products");
        List<Category> categories = categoryService.findByActive();
        ProductDto productDto = productService.getReferenceById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    };

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update Product Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong!!!");
        }

        return "redirect:/products";
    };

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableProduct(Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable Product Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong!!!");
        }

        return "redirect:/products";
    };

    @RequestMapping(value = "/deleted-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted Product Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong!!");
        }

        return "redirect:/products";
    }
}
