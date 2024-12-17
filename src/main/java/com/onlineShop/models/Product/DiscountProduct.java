package com.onlineShop.models.Product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountProduct {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Min(value = 1, message = "Minimal discount is 1%")
    @Max(value = 99, message = "Maximum discount is 99%")
    private int discount;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

}
