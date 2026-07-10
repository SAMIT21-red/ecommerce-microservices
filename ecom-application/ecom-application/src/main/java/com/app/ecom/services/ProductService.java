package com.app.ecom.services;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.entities.Product;
import com.app.ecom.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductResponse> fetchAllProducts() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> fetchProduct(Long id) {

        return repository.findById(id)
                .map(this::mapToResponse);
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();

        updateProductFromRequest(product, request);

        return mapToResponse(repository.save(product));
    }

    public Optional<ProductResponse> updateProduct(Long id,
                                                   ProductRequest request) {

        return repository.findById(id)
                .map(product -> {

                    updateProductFromRequest(product, request);

                    return mapToResponse(repository.save(product));
                });
    }

    public boolean deleteProduct(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);

        return true;
    }

    public List<ProductResponse> searchProducts(String name) {

        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        response.setCategory(product.getCategory());

        return response;
    }

    private void updateProductFromRequest(Product product,
                                          ProductRequest request) {

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
    }
}