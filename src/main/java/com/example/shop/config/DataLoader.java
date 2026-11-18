package com.example.shop.config;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Seeds a few products on first start so the shop is not empty. */
@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository products;

    public DataLoader(ProductRepository products) {
        this.products = products;
    }

    @Override
    public void run(String... args) {
        if (products.count() > 0) {
            return;
        }
        products.saveAll(List.of(
            new Product("Filter coffee powder, 500 g", "Medium roast with 20% chicory, ground for a South Indian filter.", new BigDecimal("320.00")),
            new Product("Cast iron dosa tawa", "28 cm, pre-seasoned, works on gas and induction.", new BigDecimal("1450.00")),
            new Product("Stainless steel lunch box", "Three leak-proof tiers with a carry clip.", new BigDecimal("680.00")),
            new Product("Brass diya set of 6", "Hand-finished, about 6 cm across.", new BigDecimal("540.00")),
            new Product("Cotton kitchen towels, pack of 3", "Absorbent weave, machine washable.", new BigDecimal("299.00")),
            new Product("Steel tumbler set of 4", "250 ml, rolled rim, dishwasher safe.", new BigDecimal("799.00"))
        ));
    }
}
