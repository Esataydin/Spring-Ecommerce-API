package com.esataydin.order.service;

import com.esataydin.cart.entity.CartItem;
import com.esataydin.cart.repository.CartItemRepository;
import com.esataydin.order.dto.OrderCreateRequest;
import com.esataydin.order.dto.OrderItemResponse;
import com.esataydin.order.dto.OrderResponse;
import com.esataydin.order.entity.Order;
import com.esataydin.order.entity.OrderItem;
import com.esataydin.order.exception.OrderException;
import com.esataydin.order.repository.OrderRepository;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    public OrderResponse createOrder(String userEmail, OrderCreateRequest request) {
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new OrderException("User not found"));
        
        // Create new order
        Order order = new Order(user);
        order = orderRepository.save(order);
        
        // Process order items
        for (OrderCreateRequest.OrderItemRequest itemRequest : request.getItems()) {
            // Find product
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductException("Product not found with ID: " + itemRequest.getProductId()));
            
            // Check stock
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new OrderException("Insufficient stock for product: " + product.getName() + 
                                       ". Available: " + product.getStock() + ", Requested: " + itemRequest.getQuantity());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem(order, product, itemRequest.getQuantity());
            order.addOrderItem(orderItem);
            
            // Update product stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }
        
        // Save order with items
        order = orderRepository.save(order);
        
        // Clear user's cart after successful order
        cartItemRepository.deleteByUserId(user.getId());
        
        return convertToOrderResponse(order);
    }
    
    public OrderResponse createOrderFromCart(String userEmail) {
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new OrderException("User not found"));
        
        // Get user's cart items
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        
        if (cartItems.isEmpty()) {
            throw new OrderException("Cart is empty. Cannot create order.");
        }
        
        // Create new order
        Order order = new Order(user);
        order = orderRepository.save(order);
        
        // Process cart items into order items
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            
            // Check stock
            if (product.getStock() < cartItem.getQuantity()) {
                throw new OrderException("Insufficient stock for product: " + product.getName() + 
                                       ". Available: " + product.getStock() + ", In cart: " + cartItem.getQuantity());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem(order, product, cartItem.getQuantity());
            order.addOrderItem(orderItem);
            
            // Update product stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }
        
        // Save order with items
        order = orderRepository.save(order);
        
        // Clear user's cart after successful order
        cartItemRepository.deleteByUserId(user.getId());
        
        return convertToOrderResponse(order);
    }
    
    public List<OrderResponse> getUserOrders(String userEmail) {
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new OrderException("User not found"));
        
        // Get user's orders
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }
    
    private OrderResponse convertToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(this::convertToOrderItemResponse)
                .collect(Collectors.toList());
        
        BigDecimal totalAmount = itemResponses.stream()
                .map(OrderItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Integer totalItems = itemResponses.stream()
                .mapToInt(OrderItemResponse::getQuantity)
                .sum();
        
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getCreatedAt(),
                itemResponses,
                totalAmount,
                totalItems
        );
    }
    
    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        
        return new OrderItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                orderItem.getQuantity(),
                totalPrice
        );
    }
}
