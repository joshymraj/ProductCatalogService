package com.joshlabs.productcatalogservice.controllers;

import com.joshlabs.productcatalogservice.dtos.CategoryDto;
import com.joshlabs.productcatalogservice.dtos.ProductDto;
import com.joshlabs.productcatalogservice.models.Category;
import com.joshlabs.productcatalogservice.models.Product;
import com.joshlabs.productcatalogservice.models.State;
import com.joshlabs.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Macbook Pro");
        productDto.setDescription("M1 Max");
        productDtos.add(productDto);
        return productDtos;
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable("id") int productId) {
        Product product = productService.getProductById(productId);
        return convertToProductDto(product);
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productDto;
    }

    private Product convertToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        CategoryDto categoryDto = productDto.getCategory();
        if(categoryDto != null) {
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            category.setDescription(category.getDescription());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto convertToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        Category category = product.getCategory();
        if(category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(category.getDescription());
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
