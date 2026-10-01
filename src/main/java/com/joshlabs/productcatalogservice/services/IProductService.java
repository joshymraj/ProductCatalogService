package com.joshlabs.productcatalogservice.services;

import com.joshlabs.productcatalogservice.dtos.ProductDto;
import com.joshlabs.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
}
