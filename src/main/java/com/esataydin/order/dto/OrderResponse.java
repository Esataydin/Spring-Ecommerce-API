package com.esataydin.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Long id,
    Long userId,
    LocalDateTime createdAt,
    List<OrderItemResponse> items,
    BigDecimal totalAmount,
    Integer totalItems
) {
}
