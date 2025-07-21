package com.esataydin.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long productId,
    String productName,
    BigDecimal productPrice,
    String productCategory,
    Integer quantity,
    BigDecimal totalPrice
) {
}
