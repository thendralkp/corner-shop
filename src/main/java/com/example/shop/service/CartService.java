package com.example.shop.service;

import com.example.shop.model.AppUser;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.AppUserRepository;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    static final int MAX_QUANTITY = 20;

    private final CartItemRepository cartItems;
    private final AppUserRepository users;
    private final ProductRepository products;

    public CartService(CartItemRepository cartItems, AppUserRepository users, ProductRepository products) {
        this.cartItems = cartItems;
        this.users = users;
        this.products = products;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getItems(String username) {
        return cartItems.findByUserUsernameOrderByIdAsc(username);
    }

    @Transactional(readOnly = true)
    public int count(String username) {
        return getItems(username).stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal total(List<CartItem> items) {
        return items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void add(String username, Long productId) {
        CartItem item = cartItems.findByUserUsernameAndProductId(username, productId)
                .orElseGet(() -> {
                    AppUser user = users.findByUsername(username).orElseThrow();
                    Product product = products.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
                    return new CartItem(user, product, 0);
                });
        item.setQuantity(Math.min(item.getQuantity() + 1, MAX_QUANTITY));
        cartItems.save(item);
    }

    @Transactional
    public void updateQuantity(String username, Long itemId, int quantity) {
        cartItems.findByIdAndUserUsername(itemId, username).ifPresent(item -> {
            if (quantity <= 0) {
                cartItems.delete(item);
            } else {
                item.setQuantity(Math.min(quantity, MAX_QUANTITY));
            }
        });
    }

    @Transactional
    public void remove(String username, Long itemId) {
        cartItems.findByIdAndUserUsername(itemId, username).ifPresent(cartItems::delete);
    }
}
