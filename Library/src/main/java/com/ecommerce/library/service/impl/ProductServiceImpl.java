package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

   @Autowired
   private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = new Product();

            if(imageProduct == null) {
                product.setImage(null);
            } else {
                if(imageUpload.uploadImage(imageProduct)) {
                    System.out.println("Upload Success");
                }

                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(product);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        Product product = productRepository.getReferenceById(productDto.getId());
        try {

            if(imageProduct == null) {
                product.setImage(product.getImage());
            } else {
                if(imageUpload.checkExisted(imageProduct) == false) {
                    System.out.println("Upload Image Success");
                    imageUpload.uploadImage(imageProduct);
                }
                System.out.println("Failed to upload - Image Existed!!!");
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setId(product.getId());
            product.setCategory(productDto.getCategory());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setSalePrice(productDto.getSalePrice());
            return productRepository.save(product);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDto getReferenceById(Long id) {
        Product product = productRepository.getReferenceById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory());
        productDto.setCurrentQuantity(productDto.getCurrentQuantity());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActive(product.is_active());
        return productDto;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_deleted(true);
        product.set_active(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_active(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    private Page toPage(List<ProductDto> list, Pageable pageable){
        if(pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    };

    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCategory(product.getCategory());
            productDto.setCurrentQuantity(productDto.getCurrentQuantity());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActive(product.is_active());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public List<Product> getRelatedProducts(Long categoryId) {
        return productRepository.getRelatedProducts(categoryId);
    }

    @Override
    public List<Product> getProductInCategory(Long categoryId) {
        return productRepository.getProductInCategory(categoryId);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }
}
