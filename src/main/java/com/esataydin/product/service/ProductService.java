package com.esataydin.product.service;

import com.esataydin.product.dto.ProductCreateRequest;
import com.esataydin.product.dto.ProductResponse;
import com.esataydin.product.dto.ProductUpdateRequest;
import com.esataydin.product.entity.Product;
import com.esataydin.product.exception.ProductException;
import com.esataydin.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    // Public methods (no authentication required)
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));
        return convertToResponse(product);
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }
    
    // Admin methods (require ADMIN role)
    
    public ProductResponse createProduct(ProductCreateRequest request) {
        // Check if product with same name already exists
        if (productRepository.existsByName(request.getName())) {
            throw new ProductException("Product with name '" + request.getName() + "' already exists");
        }
        
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        
        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }
    
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));
        
        // Update only non-null fields
        if (request.getName() != null) {
            // Check if another product with same name exists
            if (!product.getName().equals(request.getName()) && 
                productRepository.existsByName(request.getName())) {
                throw new ProductException("Product with name '" + request.getName() + "' already exists");
            }
            product.setName(request.getName());
        }
        
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        
        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }
    
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    // Internal methods
    
    @Transactional(readOnly = true)
    public Product getProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));
    }
    
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    
    // Utility methods
    
    private ProductResponse convertToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory()
        );
    }
}
