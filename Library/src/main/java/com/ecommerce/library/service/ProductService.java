package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();

    Product save(MultipartFile imageProduct, ProductDto productDto);

    Product update(MultipartFile imageProduct, ProductDto productDto);

    ProductDto getReferenceById(Long id);
    Optional<Product> findById(Long id);

    void deleteById(Long id);
    void enableById(Long id);
}
