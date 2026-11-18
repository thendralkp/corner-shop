package com.example.shop;

import com.example.shop.model.AppUser;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.AppUserRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // each test rolls back, so tests do not affect each other
class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired AppUserRepository users;
    @Autowired ProductRepository products;

    @Test
    void addingTheSameProductTwiceRaisesQuantity() {
        users.save(new AppUser("tester", "hash"));
        Product product = products.save(new Product("Test item", "desc", new BigDecimal("100.00")));

        cartService.add("tester", product.getId());
        cartService.add("tester", product.getId());

        List<CartItem> items = cartService.getItems("tester");
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getQuantity()).isEqualTo(2);
        assertThat(cartService.total(items)).isEqualByComparingTo("200.00");
    }

    @Test
    void settingQuantityToZeroRemovesTheItem() {
        users.save(new AppUser("tester", "hash"));
        Product product = products.save(new Product("Test item", "desc", new BigDecimal("50.00")));
        cartService.add("tester", product.getId());
        Long itemId = cartService.getItems("tester").get(0).getId();

        cartService.updateQuantity("tester", itemId, 0);

        assertThat(cartService.getItems("tester")).isEmpty();
    }
}
