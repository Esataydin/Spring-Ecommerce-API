package com.esataydin.cart.controller;

import com.esataydin.cart.dto.CartAddRequest;
import com.esataydin.cart.dto.CartItemResponse;
import com.esataydin.cart.dto.CartResponse;
import com.esataydin.cart.exception.CartException;
import com.esataydin.cart.service.CartService;
import com.esataydin.product.exception.ProductException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody CartAddRequest request,
            Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            CartItemResponse cartItem = cartService.addToCart(userEmail, request);
            return ResponseEntity.ok(cartItem);
        } catch (CartException | ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCart(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            CartResponse cart = cartService.getCart(userEmail);
            return ResponseEntity.ok(cart);
        } catch (CartException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
}
