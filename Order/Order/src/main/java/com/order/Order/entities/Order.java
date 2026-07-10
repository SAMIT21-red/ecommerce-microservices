package com.order.Order.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(name = "user_id" , nullable = false)
//    private User user;

    private Long userId;

    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status=OrderStatus.PENDING;
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItems> items = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
