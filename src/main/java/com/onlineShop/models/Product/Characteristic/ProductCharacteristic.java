package com.onlineShop.models.Product.Characteristic;

import com.onlineShop.models.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCharacteristic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_characteristic", nullable = false)
    private Characteristic characteristic;

    @NotBlank(message = "Value must not be blank")
    @Size(min = 1, max = 1000, message = "Value must be between 1 and 2000 characters")
    @Column(name = "characteristic_value", nullable = false)
    private String value;

    @Positive(message = "Number must not be empty")
    private int number;

}
