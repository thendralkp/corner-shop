package com.example.shop.repository;

import com.example.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserUsernameOrderByIdAsc(String username);
    Optional<CartItem> findByUserUsernameAndProductId(String username, Long productId);
    Optional<CartItem> findByIdAndUserUsername(Long id, String username);
}
