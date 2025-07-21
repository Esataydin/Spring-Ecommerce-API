package com.esataydin.cart.controller;

import com.esataydin.cart.dto.CartAddRequest;
import com.esataydin.cart.dto.CartItemResponse;
import com.esataydin.cart.dto.CartResponse;
import com.esataydin.cart.service.CartService;
import com.esataydin.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@Tag(name = "Cart", description = "Shopping cart management endpoints")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Add item to cart", description = "Add a product to the user's shopping cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added to cart successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or insufficient stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied - Authentication required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CartItemResponse> addToCart(
            @Valid @RequestBody CartAddRequest request,
            Authentication authentication) {
        String userEmail = authentication.getName();
        CartItemResponse cartItem = cartService.addToCart(userEmail, request);
        return ResponseEntity.ok(cartItem);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get cart contents", description = "Retrieve all items in the user's shopping cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart contents retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied - Authentication required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        String userEmail = authentication.getName();
        CartResponse cart = cartService.getCart(userEmail);
        return ResponseEntity.ok(cart);
    }
}
