package com.esataydin.order.controller;

import com.esataydin.order.dto.OrderCreateRequest;
import com.esataydin.order.dto.OrderResponse;
import com.esataydin.order.exception.OrderException;
import com.esataydin.order.service.OrderService;
import com.esataydin.product.exception.ProductException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderCreateRequest request,
            Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            OrderResponse order = orderService.createOrder(userEmail, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (OrderException | ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
    
    @PostMapping("/from-cart")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrderFromCart(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            OrderResponse order = orderService.createOrderFromCart(userEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (OrderException | ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserOrders(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            List<OrderResponse> orders = orderService.getUserOrders(userEmail);
            return ResponseEntity.ok(orders);
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred");
        }
    }
}
