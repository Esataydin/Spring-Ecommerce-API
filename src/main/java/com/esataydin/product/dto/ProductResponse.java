package com.esataydin.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    BigDecimal price,
    Integer stock,
    String category
) {
}
