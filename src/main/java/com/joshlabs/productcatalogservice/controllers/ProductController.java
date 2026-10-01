package com.joshlabs.productcatalogservice.controllers;

import com.joshlabs.productcatalogservice.dtos.CategoryDto;
import com.joshlabs.productcatalogservice.dtos.ProductDto;
import com.joshlabs.productcatalogservice.models.Category;
import com.joshlabs.productcatalogservice.models.Product;
import com.joshlabs.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {

        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(convertModelToDto(product));
        }
        return productDtos;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long productId) {
        Product product = productService.getProductById(productId);
        if(product == null) {
            throw new NullPointerException("There is no product with this id.");
        }
        return convertModelToDto(product);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product addedProduct = productService.createProduct(convertDtoToModel(productDto));
        return convertModelToDto(addedProduct);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(id, convertDtoToModel(productDto));
        return convertModelToDto(updatedProduct);
    }

    private Product convertDtoToModel(ProductDto productDto) {
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

    private ProductDto convertModelToDto(Product product) {
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
