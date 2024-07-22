package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.ProductEntity;
import com.supermarket.backend.Payload.Request.ProductRequest;
import com.supermarket.backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductEntity> searchByName(String name) {
        return productRepository.searchByName(name);
    }

    public ProductEntity getById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        boolean isExists = productRepository.existsByName(productEntity.getName());
        if (isExists) throw new RuntimeException(productEntity.getName() + " already exists.");
        return productRepository.save(productEntity);
    }

    public ProductEntity update(Integer id, ProductRequest productRequest) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) throw new RuntimeException("Product does not exist.");

        ProductEntity product = optionalProduct.get();
        product.setName(productRequest.getName());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setUnit(productRequest.getUnit());
        product.setInStock(productRequest.getInStock());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.isStatus());
        product.setCategory(productRequest.getCategory());
        product.setVendor(productRequest.getVendor());

        return productRepository.save(product);
    }

    public ProductEntity updateQuantity(Integer id, int qty) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) throw new RuntimeException("Product does not exist.");

        ProductEntity product = optionalProduct.get();

        product.setInStock(product.getInStock() + qty);

        return productRepository.save(product);
    }

    public void delete(Integer id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) throw new RuntimeException("Product does not exist.");

        productRepository.deleteById(id);
    }
}
