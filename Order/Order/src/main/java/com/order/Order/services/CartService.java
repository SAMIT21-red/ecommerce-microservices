package com.order.Order.services;


import com.order.Order.dto.CartItemRequest;
import com.order.Order.entities.CartItem;
import com.order.Order.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public boolean addToCart(Long userId, CartItemRequest request) {

        CartItem existingCartItem = cartItemRepository
                .findByUserIdAndProductId(userId, request.getProductId())
                .orElse(null);

        if (existingCartItem != null) {

            existingCartItem.setQuantity(
                    existingCartItem.getQuantity() + request.getQuantity());

            existingCartItem.setPrice(BigDecimal.valueOf(1000));

            cartItemRepository.save(existingCartItem);

            return true;
        }

        CartItem cartItem = new CartItem();

        cartItem.setUserId(userId);
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPrice(BigDecimal.valueOf(1000));

        cartItemRepository.save(cartItem);

        return true;
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public boolean removeFromCart(Long userId, Long cartItemId) {

        return cartItemRepository.findByIdAndUserId(cartItemId, userId)
                .map(cartItem -> {
                    cartItemRepository.delete(cartItem);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}