package com.onlineShop.models.Product;

import com.onlineShop.models.Product.Characteristic.ProductCharacteristic;
import com.onlineShop.models.Shop.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_shop")
    private Shop shop;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "count")
    @PositiveOrZero(message = "Count of products must be more than zero")
    private int count;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_product_characteristic", nullable = false)
    private ProductCharacteristic productCharacteristic;

}
