package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.services.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addToCart(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody CartItemRequest request) {

        boolean added = cartService.addToCart(userId, request);

        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<?> getCart(
            @RequestHeader("X-User-ID") Long userId) {

        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long cartItemId) {

        boolean removed = cartService.removeFromCart(userId, cartItemId);

        if (removed) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.notFound().build();
    }
}
