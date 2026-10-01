package com.joshlabs.productcatalogservice.services;

import com.joshlabs.productcatalogservice.dtos.FakeStoreProductDto;
import com.joshlabs.productcatalogservice.models.Category;
import com.joshlabs.productcatalogservice.models.Product;
import com.joshlabs.productcatalogservice.utils.RestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductFakeStoreAPIService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        String url = "https://fakestoreapi.com/products/{id}";
        RestTemplate restTemplate = restTemplateBuilder.build();
//        private final String url;
//        private final HttpMethod httpMethod;
//        private final Object requestBody;
//        private final String bearerToken;
//        private final MediaType mediaType;
//        private final Class<T> responseType;
//        private final ParameterizedTypeReference<T> parameterizedTypeReference;
//        private final Object[] uriVariables;
        ResponseEntity<FakeStoreProductDto> responseEntity = RestRequest.<FakeStoreProductDto>builder(restTemplate)
                .setUrl(url)
                .setHttpMethod(HttpMethod.GET)
                .setResponseType(FakeStoreProductDto.class)
                .setUriVariables(id)
                .build().execute();
        return convertFakeStoreDtoToModel(responseEntity.getBody());
    }

    @Override
    public Product createProduct(Product product) {

        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        return  null;
    }

    private FakeStoreProductDto convertModelToFakeStoreDto(Product product) {
        if(product == null) return null;
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

    private Product convertFakeStoreDtoToModel(FakeStoreProductDto fakeStoreProductDto) {
        if(fakeStoreProductDto == null) return null;

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return  product;
    }
}
