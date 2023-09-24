package com.ecommerce.customer.controller;

//import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping("/menu")
    public String  menu (Model model) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryService.findByActive();
        List<ProductDto> products = productService.products();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }

//    @GetMapping("/find-product/{id}")
//    public String findProductById(@PathVariable("id") Long id, Model model) {
//        Product product = productService.getProductById(id);
//        Long categoryId = product.getCategory().getId();
//        List<Product> products = productService.getRelatedProducts(categoryId);
//        model.addAttribute("products", product);
//        model.addAttribute("products", products);
//        return "product";
//    }

//    @GetMapping("/products-in-category/{id}")
//    public String getProducts(@PathVariable("id") Long categoryId,  Model model) {
//        Category category = categoryService.findById(categoryId);
//        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProducts();
//        List<Product> products = productService.getProductInCategory(categoryId);
//        model.addAttribute("category", category);
//        model.addAttribute("products", products);
//        model.addAttribute("categories", categoryDtoList);
//        return "shop-detail";
//    }

//    @GetMapping("/high-price")
//    public String filterHighPrice(Model model) {
//        List<Category> categories = categoryService.findByActive();
//        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProducts();
//        List<Product> products = productService.filterHighPrice();
//        model.addAttribute("products", products);
//        model.addAttribute("categoryDtoList", categoryDtoList);
//        model.addAttribute("categories", categories);
//        return "shop-detail";
//    }
}
