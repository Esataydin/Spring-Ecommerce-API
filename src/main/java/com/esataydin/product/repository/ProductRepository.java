package com.esataydin.product.repository;

import com.esataydin.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategory(String category);
    
    List<Product> findByCategoryIgnoreCase(String category);
    
    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findAllInStock();
    
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.stock > 0")
    List<Product> findByCategoryAndInStock(@Param("category") String category);
    
    @Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
    List<String> findAllCategories();
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    boolean existsByName(String name);
}
