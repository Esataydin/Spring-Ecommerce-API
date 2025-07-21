package com.esataydin.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Only include ID in equals/hashCode
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    @Column(nullable = false, length = 150)
    private String name;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @NotNull(message = "Stock is required")
    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;
    
    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String category;
    
    // Custom constructor for business logic
    public Product(String name, BigDecimal price, Integer stock, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock != null ? stock : 0;
        this.category = category;
    }
    
    // Business methods
    public boolean isInStock() {
        return stock != null && stock > 0;
    }
    
    public boolean hasStock(int quantity) {
        return stock != null && stock >= quantity;
    }
    
    public void reduceStock(int quantity) {
        if (hasStock(quantity)) {
            this.stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock. Available: " + stock + ", Requested: " + quantity);
        }
    }
    
    public void increaseStock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
        }
    }
}
