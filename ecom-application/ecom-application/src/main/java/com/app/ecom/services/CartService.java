package com.app.ecom.services;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.entities.CartItem;
import com.app.ecom.entities.Product;
import com.app.ecom.entities.User;
import com.app.ecom.repositories.CartItemRepository;
import com.app.ecom.repositories.ProductRepository;
import com.app.ecom.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(Long userId, CartItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElse(null);

        if (product == null) {
            return false;
        }

        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return false;
        }

        // Check if product already exists in cart
        CartItem existingCartItem = cartItemRepository
                .findByUserAndProduct(user, product)
                .orElse(null);

        if (existingCartItem != null) {

            existingCartItem.setQuantity(
                    existingCartItem.getQuantity() + request.getQuantity());

            existingCartItem.setPrice(
                    product.getPrice()
                            .multiply(java.math.BigDecimal.valueOf(existingCartItem.getQuantity())));

            cartItemRepository.save(existingCartItem);

            return true;
        }

        // Create new cart item
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPrice(
                product.getPrice()
                        .multiply(java.math.BigDecimal.valueOf(request.getQuantity())));

        cartItemRepository.save(cartItem);

        return true;
    }

    public List<CartItem> getCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartItemRepository.findByUser(user);
    }

    public boolean removeFromCart(Long userId, Long cartItemId) {

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return false;
        }

        CartItem cartItem = cartItemRepository
                .findByIdAndUser(cartItemId, user)
                .orElse(null);

        if (cartItem == null) {
            return false;
        }

        cartItemRepository.delete(cartItem);

        return true;
    }

    @Transactional
    public void clearCart(Long userId) {
        userRepository.findById(userId).ifPresent(cartItemRepository::deleteByUser);

    }
}