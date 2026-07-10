package com.order.Order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsdto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
