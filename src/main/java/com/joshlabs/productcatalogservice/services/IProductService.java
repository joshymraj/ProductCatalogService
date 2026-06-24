package com.joshlabs.productcatalogservice.services;

import com.joshlabs.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(int id);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
