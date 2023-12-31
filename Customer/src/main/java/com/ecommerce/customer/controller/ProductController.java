package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String  products (Model model) {
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProducts();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts =productService.listViewProducts();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        Long categoryId = product.getCategory().getId();
        List<Product> products = productService.getRelatedProducts(categoryId);
        model.addAttribute("products", product);
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProducts(@PathVariable("id") Long categoryId,  Model model) {
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProducts();
        List<Product> products = productService.getProductInCategory(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryDtoList);
        return "shop-detail";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<Category> categories = categoryService.findByActive();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProducts();
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("categories", categories);
        return "shop-detail";
    }
}
