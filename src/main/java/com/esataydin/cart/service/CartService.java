package com.esataydin.cart.service;

import com.esataydin.cart.dto.CartAddRequest;
import com.esataydin.cart.dto.CartItemResponse;
import com.esataydin.cart.dto.CartResponse;
import com.esataydin.cart.entity.CartItem;
import com.esataydin.cart.exception.CartException;
import com.esataydin.cart.repository.CartItemRepository;
import com.esataydin.product.entity.Product;
import com.esataydin.product.exception.ProductException;
import com.esataydin.product.repository.ProductRepository;
import com.esataydin.user.entity.User;
import com.esataydin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public CartItemResponse addToCart(String userEmail, CartAddRequest request) {
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CartException("User not found"));
        
        // Find product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductException("Product not found"));
        
        // Check if product has enough stock
        if (product.getStock() < request.getQuantity()) {
            throw new CartException("Insufficient stock. Available: " + product.getStock());
        }
        
        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(
                user.getId(), product.getId());
        
        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update quantity if item already exists
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            
            // Check stock again for new total quantity
            if (product.getStock() < newQuantity) {
                throw new CartException("Insufficient stock. Available: " + product.getStock() + 
                                      ", Already in cart: " + cartItem.getQuantity());
            }
            
            cartItem.setQuantity(newQuantity);
        } else {
            // Create new cart item
            cartItem = new CartItem(user, product, request.getQuantity());
        }
        
        cartItem = cartItemRepository.save(cartItem);
        return convertToCartItemResponse(cartItem);
    }
    
    public CartResponse getCart(String userEmail) {
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CartException("User not found"));
        
        // Get all cart items for user
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        
        // Convert to response DTOs
        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(this::convertToCartItemResponse)
                .collect(Collectors.toList());
        
        // Calculate totals
        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Integer totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();
        
        return new CartResponse(itemResponses, totalAmount, totalItems);
    }
    
    private CartItemResponse convertToCartItemResponse(CartItem cartItem) {
        Product product = cartItem.getProduct();
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        
        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                cartItem.getQuantity(),
                totalPrice
        );
    }
}
